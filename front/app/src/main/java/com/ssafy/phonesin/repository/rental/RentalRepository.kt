package com.ssafy.phonesin.repository.rental

import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.RentalBody
import com.ssafy.phonesin.model.RentalCountResponse
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body

interface RentalRepository {
    suspend fun getPossibleRentalCount(): NetworkResponse<RentalCountResponse, ErrorResponse>

    suspend fun postRental(@Body rentalApplylistDto: List<RentalBody>)
}