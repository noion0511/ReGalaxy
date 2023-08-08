package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

data class RentalCountResponse(
    @SerializedName("rental count")
    val rentalCount: Int,
    val status: Int
)