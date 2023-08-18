package com.ssafy.phonesin.repository.hygrometer

import com.ssafy.phonesin.model.Climate
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.model.Location
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.ApiService
import retrofit2.http.Body
import javax.inject.Inject

class HygrometerRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HygrometerRepository {
    override suspend fun getClimate(location: Location): NetworkResponse<Climate, ErrorResponse> {
        return apiService.getClimate(location)
    }
}