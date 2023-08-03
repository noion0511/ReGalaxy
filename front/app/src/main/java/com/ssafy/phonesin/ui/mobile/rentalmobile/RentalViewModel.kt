package com.ssafy.phonesin.ui.mobile.rentalmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.ui.util.base.BaseViewModel

class RentalViewModel() : BaseViewModel() {
    private val _rentalList = MutableLiveData<MutableList<Rental>>()
    val rentalList: LiveData<MutableList<Rental>> = _rentalList

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