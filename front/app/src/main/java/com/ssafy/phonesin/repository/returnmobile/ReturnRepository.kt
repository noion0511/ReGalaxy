package com.ssafy.phonesin.repository.returnmobile

import com.ssafy.phonesin.model.RentalResponse

interface ReturnRepository {
    suspend fun getRentalList(memberId: Int): List<RentalResponse>
}