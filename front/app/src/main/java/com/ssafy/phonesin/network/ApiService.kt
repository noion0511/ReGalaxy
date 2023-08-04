package com.ssafy.phonesin.network

import com.ssafy.phonesin.model.Donation
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.PhotoResponse
import com.ssafy.phonesin.model.RentalResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("/ytwok/apply")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): NetworkResponse<PhotoResponse, ErrorResponse>

    @GET("/rental/apply/list/{member_id}")
    suspend fun getRentalList(@Path("member_id") memberId: Int): List<RentalResponse>

    @POST("/donation/apply")
    suspend fun uploadDonation(@Body donationRequestDto: Donation): NetworkResponse<String, ErrorResponse>

    @GET("donation/king")
    suspend fun getKing(): NetworkResponse<PhotoResponse, ErrorResponse>

}