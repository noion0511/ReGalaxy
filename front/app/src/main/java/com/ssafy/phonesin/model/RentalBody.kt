package com.ssafy.phonesin.model

data class RentalBody(
    var climateHumidity: Boolean,
    var count: Int,
    val fund: Int,
    var homecam: Boolean,
    val rentalDeliveryLocation: String,
    val usingDate: Int,
    var y2K: Boolean
)