package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    private val repository: AddressRepository
) : BaseViewModel() {
    private val _rentalList = MutableLiveData<MutableList<Rental>>()
    val rentalList: LiveData<MutableList<Rental>> = _rentalList

    private val _addressList = mutableListOf<Address>()
    val addressList: MutableList<Address> = _addressList

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    init {
        _rentalList.value = mutableListOf()
        getAddressList()
    }

    private fun getAddressList() {
        viewModelScope.launch {
            val addressResponse = repository.getAddress(8)

            Log.e("싸피", addressResponse.toString())
            when (addressResponse) {
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
        Log.e("싸피", rental.toString())
        Log.e("싸피", _rentalList.value.toString())
    }

    fun deleteRental(id: Int) {
        _rentalList.value?.removeAt(id)
    }

    fun plusRental(id: Int) {
        _rentalList.value?.get(id)?.count = _rentalList.value?.get(id)?.count!! + 1
    }

    fun minusRental(id: Int) {
        if (_rentalList.value?.get(id)?.count!! > 1)
            _rentalList.value?.get(id)?.count = _rentalList.value?.get(id)?.count!! - 1
    }

}