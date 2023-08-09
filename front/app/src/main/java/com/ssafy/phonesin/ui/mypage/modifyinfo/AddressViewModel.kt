package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository
) : BaseViewModel() {
    private val _address = MutableLiveData<List<Address>>()
    val addressList: MutableLiveData<List<Address>>
        get() = _address

    fun postAddress(address: String) {
        viewModelScope.launch {
            Log.d("post", "postAddress: ${repository.postAddress(address)}")
        }
    }

    fun getAddress() {
        viewModelScope.launch {
            val response = repository.getAddress()
            when (response) {
                is NetworkResponse.Success -> {
                    _address.value = response.body
                }
                else -> {}
            }
        }
    }

    fun removeAddress(addressId: Int) {
        viewModelScope.launch {
            repository.removeAddress(addressId)
            getAddress()
        }
    }
}