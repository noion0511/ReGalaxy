package com.ssafy.phonesin.repository.address

import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.AgentAddress
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.NetworkResponse
import com.ssafy.phonesin.network.service.ApiService
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AddressRepository {
    override suspend fun getAddress(): NetworkResponse<List<Address>, ErrorResponse> {
        return apiService.getAddressList()
    }

    override suspend fun postAddress(address: String): NetworkResponse<String, ErrorResponse> {
        return apiService.postAddress(address)
    }

    override suspend fun removeAddress(addressId: Int): NetworkResponse<String, ErrorResponse> {
        return apiService.removeAddress(addressId)
    }

    override suspend fun getAgentAddressList(
        latitude: Double,
        longitude: Double,
    ): NetworkResponse<List<AgentAddress>, ErrorResponse> {
        return apiService.getAgentAddressList(latitude, longitude)
    }

    override suspend fun getSearchAgentAddressList(
        latitude: Double,
        longitude: Double,
        search: String
    ): NetworkResponse<List<AgentAddress>, ErrorResponse> {
        return apiService.getSearchAgentAddressList(latitude, longitude, search)
    }

}