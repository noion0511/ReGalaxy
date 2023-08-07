package com.ssafy.phonesin.repository.user

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.network.NetworkResponse

interface UserRepository {
    suspend fun signup(memberDto: MemberDto) : NetworkResponse<String, ErrorResponse>
    suspend fun verifyEmail(emailRequestDto: EmailRequestDto) : NetworkResponse<String, ErrorResponse>
    suspend fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto) : NetworkResponse<String, ErrorResponse>
}