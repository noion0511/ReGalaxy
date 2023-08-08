package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

data class RentalResponse(
    val message: String,
    @SerializedName("rental list")
    val rentalList: List<Rental>,
    val status: Int
)