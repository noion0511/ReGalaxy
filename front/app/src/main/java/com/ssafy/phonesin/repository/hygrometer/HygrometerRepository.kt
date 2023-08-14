package com.ssafy.phonesin.repository.hygrometer

import com.ssafy.phonesin.model.Climate
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Location
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body

interface HygrometerRepository {
    suspend fun getClimate(@Body location: Location) : NetworkResponse<Climate, ErrorResponse>
}