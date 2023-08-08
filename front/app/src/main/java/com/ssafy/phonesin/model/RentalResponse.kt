package com.ssafy.phonesin.model

data class RentalResponse(
    val message: String,
    val rentalList: List<Rental>,
    val status: Int
)