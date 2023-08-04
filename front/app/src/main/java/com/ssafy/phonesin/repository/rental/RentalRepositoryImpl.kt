package com.ssafy.phonesin.repository.rental

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.ApiService
import com.ssafy.phonesin.network.NetworkResponse
import javax.inject.Inject

class RentalRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RentalRepository {
    override suspend fun getPossibleRentalCount(memberId: Int): NetworkResponse<Int, ErrorResponse> {
        return apiService.getPossibleRentalCount(memberId)
    }

}