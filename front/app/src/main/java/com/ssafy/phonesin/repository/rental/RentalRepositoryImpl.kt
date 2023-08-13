package com.ssafy.phonesin.repository.rental

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.RentalBody
import com.ssafy.phonesin.model.RentalCountResponse
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.ApiService
import javax.inject.Inject

class RentalRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RentalRepository {
    override suspend fun getPossibleRentalCount(): NetworkResponse<RentalCountResponse, ErrorResponse> {
        return apiService.getPossibleRentalCount()
    }

    override suspend fun postRental(rentalApplylistDto: List<RentalBody>) {
        return apiService.postRental(rentalApplylistDto)
    }
}