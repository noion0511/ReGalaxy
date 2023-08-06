package com.ssafy.phonesin.repository.returnmobile

import com.ssafy.phonesin.model.RentalResponse
import com.ssafy.phonesin.model.Return
import com.ssafy.phonesin.network.ApiService
import javax.inject.Inject

class ReturnRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ReturnRepository {

    override suspend fun getRentalList(memberId: Int): List<RentalResponse> {
        return apiService.getRentalList(memberId)
    }

    override suspend fun postReturnList(returnList: List<Return>) {
        return apiService.postReturn(returnList)
    }
}