package com.ssafy.phonesin.ui.mobile.returnmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.repository.returnmobile.ReturnRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnMobileViewModel @Inject constructor(
    private val returnRepository: ReturnRepository,
) : BaseViewModel() {
    private val _rentalResponseList = MutableLiveData<List<Rental>>()
    val rentalResponseList: LiveData<List<Rental>>
        get() = _rentalResponseList

    init {
        getRentalList()
    }

    fun getRentalList() {
        viewModelScope.launch {
            _rentalResponseList.value = returnRepository.getRentalList().rentalList.filter { it.rentalStatus>=4 }
        }
    }

}