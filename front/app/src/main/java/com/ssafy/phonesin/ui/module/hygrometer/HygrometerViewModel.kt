package com.ssafy.phonesin.ui.module.hygrometer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Climate
import com.ssafy.phonesin.model.Location
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.hygrometer.HygrometerRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HygrometerViewModel @Inject constructor(
    private val hygrometerRepository: HygrometerRepository
) : BaseViewModel() {
    private val _climate = MutableLiveData<Climate>()
    val nowClimate : MutableLiveData<Climate>
        get() = _climate

    fun getClimate(location: Location) {
        viewModelScope.launch {
            val response = hygrometerRepository.getClimate(location)
            when(response) {
                is NetworkResponse.Success -> {
                    _climate.value = response.body
                }
                else -> {}
            }
        }
    }
}