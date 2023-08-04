package com.ssafy.phonesin.repository.rental

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.NetworkResponse

interface RentalRepository {
    suspend fun getPossibleRentalCount(memberId: Int): NetworkResponse<Int, ErrorResponse>
}