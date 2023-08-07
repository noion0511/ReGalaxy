package com.ssafy.phonesin.repository.login

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.network.ApiService
import com.ssafy.phonesin.network.NetworkResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {
    override suspend fun login(loginRequestDto: LoginRequestDto): NetworkResponse<Token, ErrorResponse> {
        return apiService.login(loginRequestDto)
    }

}