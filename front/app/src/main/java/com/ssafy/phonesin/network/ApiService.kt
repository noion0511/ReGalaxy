package com.ssafy.phonesin.network

import android.provider.ContactsContract
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.LoginRequestDto
import com.ssafy.phonesin.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.lang.reflect.Member

interface ApiService {
    @Multipart
    @POST("/ytwok/apply")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): NetworkResponse<PhotoResponse, ErrorResponse>

    @POST("/member/login")
    suspend fun makeNickName(@Body loginRequestDto : LoginRequestDto) : NetworkResponse<Member, ErrorResponse>
}