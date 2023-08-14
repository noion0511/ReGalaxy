package com.ssafy.phonesin.repository.donation

import com.ssafy.phonesin.model.Donation
import com.ssafy.phonesin.model.DonationCount
import com.ssafy.phonesin.model.DonationRank
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.service.ApiService
import com.ssafy.phonesin.network.NetworkResponse
import javax.inject.Inject

class DonationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DonationRepository {
    override suspend fun uploadDonation(donation: Donation): NetworkResponse<String, ErrorResponse> {
        return apiService.uploadDonation(donation)
    }

    override suspend fun getRank(): NetworkResponse<DonationRank, ErrorResponse> {
        return apiService.getRank()
    }

    override suspend fun getTotalDonationCount(): NetworkResponse<DonationCount, ErrorResponse> {
        return apiService.getTotalDonationCount()
    }
}