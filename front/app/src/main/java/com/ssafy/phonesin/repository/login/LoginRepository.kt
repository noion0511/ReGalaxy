package com.ssafy.phonesin.repository.login

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.PhotoResponse
import com.ssafy.phonesin.network.NetworkResponse
import okhttp3.MultipartBody

class LoginRepository {
    suspend fun uploadImage(file : MultipartBody.Part): NetworkResponse<PhotoResponse, ErrorResponse>

}