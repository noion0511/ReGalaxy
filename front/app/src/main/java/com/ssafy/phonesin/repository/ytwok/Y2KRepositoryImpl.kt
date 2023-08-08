package com.ssafy.phonesin.repository.ytwok

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.PhotoResponse
import com.ssafy.phonesin.network.ApiService
import com.ssafy.phonesin.network.NetworkResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class Y2KRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Y2KRepository {
    override suspend fun uploadImage(file: MultipartBody.Part): NetworkResponse<PhotoResponse, ErrorResponse> {
        return apiService.uploadImage(file)
    }
}