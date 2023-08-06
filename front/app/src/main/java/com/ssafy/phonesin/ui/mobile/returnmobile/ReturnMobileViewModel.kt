package com.ssafy.phonesin.ui.mobile.returnmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.RentalResponse
import com.ssafy.phonesin.repository.returnmobile.ReturnRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnMobileViewModel @Inject constructor(
    private val returnRepository: ReturnRepository,
) : BaseViewModel() {
    private val _rentalResponseList = MutableLiveData<List<RentalResponse>>()
    val rentalResponseList: LiveData<List<RentalResponse>>
        get() = _rentalResponseList

    init {
        getRentalList()
    }

    fun getRentalList() {
        viewModelScope.launch {
            //_rentalResponseList.value = returnRepository.getRentalList(MEMBER_ID)

            val temp = mutableListOf<RentalResponse>()
            temp.add(RentalResponse(20000, "갤럭시 s3", 123, "", "", 11, "", 1, ""))
            temp.add(RentalResponse(20000, "갤럭시 s4", 777, "", "", 21, "", 1, ""))
            temp.add(RentalResponse(20000, "갤럭시 s5", 999, "", "", 8, "", 1, ""))
            _rentalResponseList.value = temp
        }
    }

}