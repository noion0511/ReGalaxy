package com.ssafy.phonesin.ui.mobile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.AgentAddress
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

    private val _agentAddress = MutableLiveData<MutableList<AgentAddress>>()
    val agentAddress: LiveData<MutableList<AgentAddress>> = _agentAddress


    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    init {
        _addressList.add(Address("기존 주소지", -1))
    }

    fun getAddressList() {
        viewModelScope.launch {
            when (val addressResponse = addressRepository.getAddress()) {
                is NetworkResponse.Success -> {
                    val body = addressResponse.body.toMutableList()
                    if(body.size!=0)
                        _addressList = body
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

    fun getAgentAddress(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            when (val response = addressRepository.getAgentAddressList(latitude, longitude)) {
                is NetworkResponse.Success -> {
                    _agentAddress.value = response.body.toMutableList()
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

    fun getSearchAgentAddress(
        latitude: Double,
        longitude: Double, search: String
    ) {
        viewModelScope.launch {
            when (val response =
                addressRepository.getSearchAgentAddressList(latitude, longitude, search)) {
                is NetworkResponse.Success -> {
                    _agentAddress.value = response.body.toMutableList()
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