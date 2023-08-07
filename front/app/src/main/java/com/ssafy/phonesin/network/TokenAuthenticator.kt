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

class TokenAuthenticator @Inject constructor(private val repository: LoginRepository) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401) {
            val newAccessToken = runBlocking {
                fetchNewAccessToken()
            }

            if (newAccessToken != null) {
                return response.request().newBuilder()
                    .header("Authorization", "$newAccessToken")
                    .build()
            }
        }

        return null
    }


    private suspend fun fetchNewAccessToken(): String? = withContext(Dispatchers.IO) {
        try {
            val response = repository.refreshAccessToken(Token(getAccessToken(), getRefreshToken()))
            val answer = when (response) {
                is NetworkResponse.Success -> {
                    initJwtToken(response.body)
                    response.body.accessToken
                }
                else -> null
            }
            return@withContext answer
        } catch (e: Exception) {
            return@withContext null
        }
    }
}
