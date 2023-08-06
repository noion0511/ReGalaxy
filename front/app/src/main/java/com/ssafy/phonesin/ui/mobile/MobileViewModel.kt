package com.ssafy.phonesin.ui.mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.ApplicationClass.Companion.MEMBER_ID
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : BaseViewModel() {
    private var _addressList = mutableListOf<Address>()
    val addressList: MutableList<Address>
        get() = _addressList


    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    fun getAddressList() {
        viewModelScope.launch {
            when (val addressResponse = addressRepository.getAddress(MEMBER_ID)) {
                is NetworkResponse.Success -> {
                    _addressList = addressResponse.body.toMutableList()
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
}