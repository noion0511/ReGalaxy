package com.ssafy.phonesin.repository.user

import com.ssafy.phonesin.model.BaseResponse
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.MemberDto
import com.ssafy.phonesin.model.MessageResponse
import com.ssafy.phonesin.model.UserRentalResponse
import com.ssafy.phonesin.model.UserReturnResponse
import com.ssafy.phonesin.model.UserDonationResponse
import com.ssafy.phonesin.model.UserModify
import com.ssafy.phonesin.model.UserResponse
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.UserApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
) : UserRepository {
    override suspend fun signup(memberDto: MemberDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.signup(memberDto)
    }

    override suspend fun verifyEmail(emailRequestDto: EmailRequestDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.verifyEmail(emailRequestDto)
    }

    override suspend fun verifyEmailConfirm(emailCheckRequestDto: EmailCheckRequestDto): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.verifyEmailConfirm(emailCheckRequestDto)
    }

    override suspend fun withdrawl(): NetworkResponse<BaseResponse, ErrorResponse> {
        return apiService.withdrawal()
    }

    override suspend fun getUserInfo(): UserResponse {
        return apiService.getUserInfo()
    }

    override suspend fun updateUserInfo(newInfo: UserModify): NetworkResponse<MessageResponse, ErrorResponse> {
        return apiService.updateUserInfo(newInfo)
    }

    override suspend fun getUserDonationList(): NetworkResponse<UserDonationResponse, ErrorResponse> {
        return apiService.getUserDonationList()
    }

    override suspend fun cancelUserDonation(donationId: Int): NetworkResponse<MessageResponse, ErrorResponse> {
        return apiService.cancelUserDonation(donationId)
    }

    override suspend fun getUserRentalList(): NetworkResponse<UserRentalResponse, ErrorResponse> {
        return apiService.getUserRentalList()
    }

    override suspend fun cancelUserRental(rentalId: Int): NetworkResponse<MessageResponse, ErrorResponse> {
        return apiService.cancelUserRental(rentalId)
    }

    override suspend fun getUserReturnList(): NetworkResponse<UserReturnResponse, ErrorResponse> {
        return apiService.getUserReturnList()
    }

    override suspend fun cancelUserReturn(returnId: Int): NetworkResponse<MessageResponse, ErrorResponse> {
        return apiService.cancelUserReturn(returnId)
    }
}