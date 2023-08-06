package com.ssafy.phonesin.repository.returnmobile

import com.ssafy.phonesin.model.RentalResponse
import com.ssafy.phonesin.model.Return

interface ReturnRepository {
    suspend fun getRentalList(memberId: Int): List<RentalResponse>
    suspend fun postReturnList(returnList: List<Return>)
}