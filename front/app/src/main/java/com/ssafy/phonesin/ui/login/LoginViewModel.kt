package com.ssafy.phonesin.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.login.LoginRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _token = MutableLiveData<Event<Token>>()
    val token: LiveData<Event<Token>> = _token

    fun login(loginRequestDto: LoginRequestDto) {
        viewModelScope.launch {

            val response =  repository.login(loginRequestDto)

            val type = "로그인에"
            when (response) {
                is NetworkResponse.Success -> {
                    _token.postValue(Event(response.body))
                }

                is NetworkResponse.ApiError -> {
                    _msg.postValue(postValueEvent(0, type))
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, type))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, type))
                }
            }
        }
    }

    fun checkValidation(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            _msg.postValue(Event("이메일을 입력해 주세요."))
            return false
        }

        if(password.isEmpty()) {
            _msg.postValue(Event("비밀번호를 입력해 주세요."))
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _msg.postValue(Event("잘못된 이메일 형식입니다."))
            return false
        }

        return true
    }
}