package com.ssafy.phonesin.repository.login

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.AuthApiService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
) : LoginRepository {
    override suspend fun login(loginRequestDto: LoginRequestDto): NetworkResponse<Token, ErrorResponse> {
        return apiService.login(loginRequestDto)
    }

    override suspend fun refreshAccessToken(token: Token): NetworkResponse<Token, ErrorResponse> {
        return apiService.refreshAccessToken(token)
    }

}