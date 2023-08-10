package com.ssafy.phonesin.ui.mobile.donatemobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.AgentAddress
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.address.AddressRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateAgentViewModel @Inject constructor(
    private val repository: AddressRepository
) : BaseViewModel() {
    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _agentAddress = MutableLiveData<MutableList<AgentAddress>>()
    val agentAddress: LiveData<MutableList<AgentAddress>> = _agentAddress

    init {
//        val temp = mutableListOf<AgentAddress>(
//            AgentAddress(
//                "구미 서비스 센터",
//                "경상북도 구미시 인동가산로 9-3 노블레스타워 1층",
//                1234,
//                36.111179,
//                128.413741
//            )
//        )
//        _agentAddress.value = temp
    }

    fun getAgentAddress(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            when (val response = repository.getAgentAddressList(latitude, longitude)) {
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
                repository.getSearchAgentAddressList(latitude, longitude, search)) {
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