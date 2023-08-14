package com.ssafy.phonesin.repository.address

import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.AgentAddress
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.NetworkResponse
import retrofit2.http.Body

interface AddressRepository {
    suspend fun getAddress(): NetworkResponse<List<Address>, ErrorResponse>

    suspend fun postAddress(@Body address: String): NetworkResponse<String, ErrorResponse>

    suspend fun removeAddress(@Body addressId: Int): NetworkResponse<String, ErrorResponse>

    suspend fun getAgentAddressList(
        latitude: Double,
        longitude: Double,
    ): NetworkResponse<List<AgentAddress>, ErrorResponse>

    suspend fun getSearchAgentAddressList(
        latitude: Double,
        longitude: Double, search: String
    ): NetworkResponse<List<AgentAddress>, ErrorResponse>
}