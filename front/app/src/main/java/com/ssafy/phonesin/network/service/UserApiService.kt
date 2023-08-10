package com.ssafy.phonesin.network.service

import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.model.MessageResponse
import com.ssafy.phonesin.model.UserDonationResponse
import com.ssafy.phonesin.model.UserModify
import com.ssafy.phonesin.model.UserResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @POST("/member/signup")
    suspend fun signup(@Body memberDto: MemberDto): NetworkResponse<BaseResponse, ErrorResponse>

    @POST("/member/email-verification")
    suspend fun verifyEmail(@Body emailRequestDto: EmailRequestDto): NetworkResponse<BaseResponse, ErrorResponse>

    @POST("/member/email-verification/confirm")
    suspend fun verifyEmailConfirm(@Body emailCheckRequestDto: EmailCheckRequestDto): NetworkResponse<BaseResponse, ErrorResponse>

    @PUT("/member/delete")
    suspend fun withdrawal(): NetworkResponse<BaseResponse, ErrorResponse>

    @GET("/member/info")
    suspend fun getUserInfo(): UserResponse

    @PUT("/member/update")
    suspend fun updateUserInfo(@Body newInfo: UserModify): NetworkResponse<MessageResponse, ErrorResponse>

    @GET("/donation/list")
    suspend fun getUserDonationList(): NetworkResponse<UserDonationResponse, ErrorResponse>

    @DELETE("/donation/delete/{donationId}")
    suspend fun cancelUserDonation(@Path("donationId") donationId: Int): NetworkResponse<MessageResponse, ErrorResponse>
}