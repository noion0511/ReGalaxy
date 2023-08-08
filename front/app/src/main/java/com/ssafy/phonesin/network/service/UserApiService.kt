package com.ssafy.phonesin.network.service

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/member/signup")
    suspend fun signup(@Body memberDto: MemberDto) : NetworkResponse<String, ErrorResponse>

    @POST("/member/email-verification")
    suspend fun verifyEmail(@Body emailRequestDto: EmailRequestDto) : NetworkResponse<String, ErrorResponse>

    @POST("/member/email-verification/confirm")
    suspend fun verifyEmailConfirm(@Body emailCheckRequestDto: EmailCheckRequestDto) : NetworkResponse<String, ErrorResponse>
}