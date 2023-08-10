package com.ssafy.phonesin.repository.user

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
import retrofit2.http.Path

interface UserRepository {
    suspend fun signup(memberDto: MemberDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun verifyEmail(emailRequestDto: EmailRequestDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun withdrawl(): NetworkResponse<BaseResponse, ErrorResponse>
    suspend fun getUserInfo(): UserResponse
    suspend fun updateUserInfo(@Body newInfo: UserModify): NetworkResponse<MessageResponse, ErrorResponse>
    suspend fun getUserDonationList(): NetworkResponse<UserDonationResponse, ErrorResponse>
    suspend fun cancelUserDonation(@Body donationId: Int): NetworkResponse<MessageResponse, ErrorResponse>
}