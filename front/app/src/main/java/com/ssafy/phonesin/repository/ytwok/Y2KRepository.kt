package com.ssafy.phonesin.repository.ytwok

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.PhotoResponse
import com.ssafy.phonesin.network.NetworkResponse
import okhttp3.MultipartBody

interface Y2KRepository {
    suspend fun uploadImage(file : MultipartBody.Part): NetworkResponse<PhotoResponse, ErrorResponse>
}