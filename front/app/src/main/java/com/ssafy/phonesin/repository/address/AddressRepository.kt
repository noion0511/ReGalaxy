package com.ssafy.phonesin.repository.address

import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.model.ErrorResponse
import com.ssafy.phonesin.network.NetworkResponse

interface AddressRepository {
    suspend fun getAddress(): NetworkResponse<List<Address>, ErrorResponse>


}