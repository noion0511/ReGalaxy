package com.ssafy.phonesin.ui.mypage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.User
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.repository.user.UserRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _address = MutableLiveData<List<Address>>()
    val addressList: MutableLiveData<List<Address>>
        get() = _address

    private val _user = MutableLiveData<User>()
    val user: MutableLiveData<User>
        get() = _user

    fun postAddress(address: String) {
        viewModelScope.launch {
            addressRepository.postAddress(address)
        }
    }

    fun getAddress() {
        viewModelScope.launch {
            val response = addressRepository.getAddress()
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
            addressRepository.removeAddress(addressId)
            getAddress()
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            userRepository.withdrawl()
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val response = userRepository.getUserInfo()
            if (response.status == 200) {
                _user.value = response.member
            }
        }
    }
}