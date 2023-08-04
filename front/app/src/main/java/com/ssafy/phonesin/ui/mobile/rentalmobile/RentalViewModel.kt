package com.ssafy.phonesin.ui.mobile.rentalmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.repository.rental.RentalRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val rentalRepository: RentalRepository
) : BaseViewModel() {
    private val _rentalList = MutableLiveData<MutableList<Rental>>()
    val rentalList: LiveData<MutableList<Rental>> = _rentalList

    private val _addressList = mutableListOf<Address>()
    val addressList: MutableList<Address> = _addressList

    private val _possibleRentalCount = MutableLiveData<Int>()
    val possibleRentalCount: LiveData<Int> = _possibleRentalCount

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    init {
        _rentalList.value = mutableListOf()
        getAddressList()
        getPossibleRentalCount()
    }

    private fun getPossibleRentalCount() {
        viewModelScope.launch {
            when (val rentalCountResponse = rentalRepository.getPossibleRentalCount(8)) {
                is NetworkResponse.Success -> {
                    _possibleRentalCount.value = 10 - rentalCountResponse.body
                }

                is NetworkResponse.ApiError -> {
                    _msg.postValue(postValueEvent(0, "_possibleRentalCount"))
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, "_possibleRentalCount"))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, "_possibleRentalCount"))
                }
            }
        }
    }


    private fun getAddressList() {
        viewModelScope.launch {
            when (val addressResponse = addressRepository.getAddress(8)) {
                is NetworkResponse.Success -> {
                    _addressList.addAll(addressResponse.body)
                }

                is NetworkResponse.ApiError -> {
                    _msg.postValue(postValueEvent(0, "addressList"))
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, "addressList"))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, "addressList"))
                }
            }
        }
    }

    fun addRental(rental: Rental) {
        _rentalList.value?.add(rental)
    }

    fun deleteRental(id: Int) {
        val updatedList = _rentalList.value!!.toMutableList()
        updatedList.removeAt(id)
        _rentalList.value = updatedList
    }

    fun plusRental(id: Int) {
        val updatedList = _rentalList.value!!.toMutableList()
        updatedList[id].count = updatedList[id].count + 1
        _rentalList.value = updatedList
    }

    fun minusRental(id: Int) {
        if (_rentalList.value?.get(id)?.count!! > 1) {
            val updatedList = _rentalList.value!!.toMutableList()
            updatedList[id].count = updatedList[id].count - 1
            _rentalList.value = updatedList
        }
    }

}