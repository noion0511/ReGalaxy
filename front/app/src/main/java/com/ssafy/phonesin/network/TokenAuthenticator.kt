package com.ssafy.phonesin.network

import com.ssafy.phonesin.common.AppPreferences.getAccessToken
import com.ssafy.phonesin.common.AppPreferences.getRefreshToken
import com.ssafy.phonesin.common.AppPreferences.initJwtToken
import com.ssafy.phonesin.model.Token
import com.ssafy.phonesin.repository.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenManager @Inject constructor(private val apiServiceHolder: ApiServiceHolder) {

    suspend fun fetchNewAccessToken(): String? {
        val apiService = apiServiceHolder.apiService ?: return null
        return try {
            val response = apiService.refreshAccessToken(Token(getAccessToken(), getRefreshToken()))
            when (response) {
                is NetworkResponse.Success -> {
                    initJwtToken(response.body)
                    response.body.accessToken
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

class TokenAuthenticator @Inject constructor(private val tokenManager: TokenManager) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            val newAccessToken = runBlocking {
                tokenManager.fetchNewAccessToken()
            } ?: return null // 로그아웃 또는 적절한 처리를 수행합니다.

            return response.request().newBuilder()
                .header("Authorization", "$newAccessToken")
                .build()
        }

        return null
    }
}

