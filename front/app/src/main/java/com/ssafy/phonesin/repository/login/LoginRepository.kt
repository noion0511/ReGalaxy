package com.ssafy.phonesin.repository.login

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.NetworkResponse

interface LoginRepository {
    suspend fun login(loginRequestDto : LoginRequestDto) : NetworkResponse<Token, ErrorResponse>
    suspend fun refreshAccessToken(token: Token) : NetworkResponse<Token, ErrorResponse>

}