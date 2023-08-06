package com.ssafy.phonesin.ui.mobile.returnmobile

import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Return
import com.ssafy.phonesin.repository.returnmobile.ReturnRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnViewModel @Inject constructor(
    private val repository: ReturnRepository
) : BaseViewModel() {
    private val _returnList = mutableListOf<Return>()
    val returnList: MutableList<Return>
        get() = _returnList

    fun uploadReturn() {
        viewModelScope.launch {
//              repository.postReturnList(_returnList)
        }
    }

    fun setReturnList(id: List<Int>) {
        _returnList.clear()
        id.forEach {
            _returnList.add(Return("", "", "", -1, -1, "", it, true, -1, ""))
        }
    }

    fun setReturnListContent(content: String) {
        _returnList.forEach {
            it.review = content
        }
    }

    fun setReturnListType(type: String) {
        _returnList.forEach {
            it.backDeliveryLocationType = type
        }
    }

    fun setReturnListDate(date: String) {
        _returnList.forEach {
            it.backDeliveryDate = date
        }
    }

    fun setReturnListAddress(address: String) {
        _returnList.forEach {
            it.backDeliveryLocation = address
        }
    }

}