package com.ssafy.phonesin.ui.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.ConfirmEmail
import com.ssafy.phonesin.model.EmailValidation
import com.ssafy.phonesin.model.Event
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.model.MemberValidation
import com.ssafy.phonesin.model.SignUpInformation
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.repository.user.UserRepository
import com.ssafy.phonesin.ui.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignupViewModel"

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _msg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>> = _msg

    private val _signupResponse = MutableLiveData<Event<BaseResponse>>()
    val signupResponse: LiveData<Event<BaseResponse>> = _signupResponse

    private val _emailConfirm = MutableLiveData<Event<String>>()
    val emailConfirm: LiveData<Event<String>> = _emailConfirm

    private val _emailCheck = MutableLiveData<Event<String>>()
    val emailCheck: LiveData<Event<String>> = _emailCheck

    private val _emailCheckReTry = MutableLiveData<Event<String>>()
    val emailCheckReTry: LiveData<Event<String>> = _emailCheckReTry

    private val _memberDto = MutableLiveData<SignUpInformation>()
    val memberDto: LiveData<SignUpInformation> = _memberDto

    private val _emailConfirmStatus = MutableLiveData<Pair<ConfirmEmail, String>>()
    val emailConfirmStatus: LiveData<Pair<ConfirmEmail, String>> = _emailConfirmStatus

    fun setUserInputEmail(info: SignUpInformation) {
        _memberDto.value = info
    }

    fun setEmailConfirmStatus(status: Pair<ConfirmEmail, String>) {
        _emailConfirmStatus.value = status
    }

    fun signup(memberDto: MemberDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.signup(memberDto)
            Log.d(TAG, "signup: $response")

            val type = "signup"
            when (response) {
                is NetworkResponse.Success -> {
                    _signupResponse.postValue(Event(response.body))
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
            hideProgress()
        }
    }


    fun verifyEmail(emailRequestDto: EmailRequestDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.verifyEmail(emailRequestDto)
            Log.d(TAG, "verifyEmail: $response")

            val type = "이메일 인증"
            when (response) {
                is NetworkResponse.Success -> {
                    _emailCheck.postValue(Event(response.body.message))
                }

                is NetworkResponse.ApiError -> {
                    if(response.body.status == "409") {
                        _emailCheck.postValue(Event(response.body.message))
                    } else {
                        _msg.postValue(postValueEvent(0, type))
                    }
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, type))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, type))
                }
            }
            hideProgress()
        }
    }

    fun verifyEmailCheck(emailRequestDto: EmailRequestDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.verifyEmail(emailRequestDto)
            Log.d(TAG, "verifyEmail: $response")

            val type = "이메일 인증"
            when (response) {
                is NetworkResponse.Success -> {
                    _emailCheckReTry.postValue(Event(response.body.message))
                }

                is NetworkResponse.ApiError -> {
                    if(response.body.status == "409") {
                        _emailCheckReTry.postValue(Event(response.body.message))
                    } else {
                        _msg.postValue(postValueEvent(0, type))
                    }
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, type))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, type))
                }
            }
            hideProgress()
        }
    }

    fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto) {
        showProgress()
        viewModelScope.launch {
            val response = repository.verifyEmailConfirm(emailCheckRequestDto)
            Log.d(TAG, "verifyEmailConfirm: $response")

            val type = "이메일 인증 확인"
            when (response) {
                is NetworkResponse.Success -> {
                    _emailConfirm.postValue(Event(response.body.message))
                }

                is NetworkResponse.ApiError -> {
                    _msg.postValue(Event(response.body.message))
                }

                is NetworkResponse.NetworkError -> {
                    _msg.postValue(postValueEvent(1, type))
                }

                is NetworkResponse.UnknownError -> {
                    _msg.postValue(postValueEvent(2, type))
                }
            }
            hideProgress()
        }
    }

    fun validateMember(signUp: SignUpInformation): List<MemberValidation> {
        val validationErrors = mutableListOf<MemberValidation>()

        if (!Patterns.EMAIL_ADDRESS.matcher(signUp.email).matches()) {
            validationErrors.add(MemberValidation.INVALID_EMAIL_FORMAT)
        }

        if (signUp.password.length < 8) {
            validationErrors.add(MemberValidation.SHORT_PASSWORD)
        }

        if (signUp.password != signUp.passwordCheck) {
            validationErrors.add(MemberValidation.PASSWORD_MISMATCH)
        }

        if (signUp.email.isEmpty()) {
            validationErrors.add(MemberValidation.EMPTY_EMAIL)
        }

        if (signUp.memberName.isEmpty()) {
            validationErrors.add(MemberValidation.NO_NAME)
        }

        return validationErrors
    }

    fun validateEmail(email: String): EmailValidation {
        if (email.isEmpty()) {
            return EmailValidation.EMPTY_EMAIL
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return EmailValidation.INVALID_EMAIL_FORMAT
        }

        return EmailValidation.VALID_EMAIL_FORMAT
    }
}