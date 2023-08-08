package com.ssafy.phonesin.repository.returnmobile

import com.ssafy.phonesin.model.RentalResponse
import com.ssafy.phonesin.model.Return

interface ReturnRepository {
    suspend fun getRentalList(): RentalResponse
    suspend fun postReturnList(returnList: List<Return>)
}