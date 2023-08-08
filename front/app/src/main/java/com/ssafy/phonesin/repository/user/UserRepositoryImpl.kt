package com.ssafy.phonesin.repository.user

import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.UserApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
) : UserRepository {
    override suspend fun signup(memberDto: MemberDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.signup(memberDto)
    }

    override suspend fun verifyEmail(emailRequestDto: EmailRequestDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.verifyEmail(emailRequestDto)
    }

    override suspend fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.verifyEmailConfirm(emailCheckRequestDto)
    }
}