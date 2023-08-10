package com.ssafy.phonesin.repository.address

import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.service.ApiService
import com.ssafy.phonesin.network.NetworkResponse
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AddressRepository {
    override suspend fun getAddress(memberId: Int): NetworkResponse<List<Address>, ErrorResponse> {
        return apiService.getAddressList(memberId)
    }

}