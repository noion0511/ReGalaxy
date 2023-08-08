package com.ssafy.phonesin.repository.rental

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body

interface RentalRepository {
    suspend fun getPossibleRentalCount(memberId: Int): NetworkResponse<Int, ErrorResponse>

    suspend fun postRental(@Body rentalApplylistDto:List<Rental>)
}