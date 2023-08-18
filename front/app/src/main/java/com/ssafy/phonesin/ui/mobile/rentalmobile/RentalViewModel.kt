package com.ssafy.phonesin.ui.mobile.rentalmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.RentalBody
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.rental.RentalRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    private val rentalRepository: RentalRepository
) : BaseViewModel() {
    private val _rentalList = MutableLiveData<MutableList<RentalBody>>()
    val rentalList: LiveData<MutableList<RentalBody>>
        get() = _rentalList

    private val _possibleRentalCount = MutableLiveData<Int>()
    val possibleRentalCount: LiveData<Int>
        get() = _possibleRentalCount

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    init {
        _rentalList.value = mutableListOf()
//        getAddressList()
//        getPossibleRentalCount()
    }

    fun postRental() {
        viewModelScope.launch {
            _rentalList.value?.let { rentalRepository.postRental(it.toList()) }
        }
    }

    fun getPossibleRentalCount() {
        viewModelScope.launch {
            when (val rentalCountResponse = rentalRepository.getPossibleRentalCount()) {
                is NetworkResponse.Success -> {
                    _possibleRentalCount.value = 10 - rentalCountResponse.body.rentalCount
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

    fun clearRentalList() {
        _rentalList.value = mutableListOf()
    }


    fun addRental(rental: RentalBody) {
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

    fun currentRentalListSize(): Int? {
        return _rentalList.value?.sumOf { it.count }
    }

}