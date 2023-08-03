package com.ssafy.phonesin.network

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/ytwok/apply")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): NetworkResponse<PhotoResponse, ErrorResponse>
}