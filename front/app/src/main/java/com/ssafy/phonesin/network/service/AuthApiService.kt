package com.ssafy.phonesin.network.service

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/member/token/refresh")
    suspend fun refreshAccessToken(@Body token: Token) : NetworkResponse<Token, ErrorResponse>

    @POST("/member/login")
    suspend fun login(@Body loginRequestDto : LoginRequestDto) : NetworkResponse<Token, ErrorResponse>
}