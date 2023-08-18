package com.ssafy.phonesin.repository.donation

import com.ssafy.phonesin.model.Donation
import com.ssafy.phonesin.model.DonationCount
import com.ssafy.phonesin.model.DonationRank
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.NetworkResponse

interface DonationRepository {
    suspend fun uploadDonation(donation: Donation): NetworkResponse<String, ErrorResponse>
    suspend fun getRank(): NetworkResponse<DonationRank, ErrorResponse>
    suspend fun getTotalDonationCount() : NetworkResponse<DonationCount, ErrorResponse>
}