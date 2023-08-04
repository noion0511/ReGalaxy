package com.ssafy.phonesin.model

data class RentalResponse(
    val fund: Int,
    val modelName: Any,
    val phoneId: Any,
    val rentalDeliveryLocation: String,
    val rentalEnd: String,
    val rentalId: Int,
    val rentalStart: String,
    val rentalStatus: Int,
    val waybillNumber: String
)