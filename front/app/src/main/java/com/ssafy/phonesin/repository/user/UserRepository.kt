package com.ssafy.phonesin.repository.user

import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.model.UserResponse
import com.ssafy.phonesin.network.NetworkResponse

interface UserRepository {
    suspend fun signup(memberDto: MemberDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun verifyEmail(emailRequestDto: EmailRequestDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun withdrawl(): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun getUserInfo(): UserResponse
}