package com.ssafy.phonesin.model

data class Rental(
    val applyDate: String,
    val climateHumidity: Boolean,
    val fund: Int,
    val homecam: Boolean,
    val modelName: Any,
    val phoneId: Any,
    val rentalDeliveryLocation: String,
    val rentalEnd: Any,
    val rentalId: Int,
    val rentalStart: Any,
    val rentalStatus: Int,
    val waybillNumber: Any,
    val y2K: Boolean
) {
    constructor() : this(
        applyDate = "",
        climateHumidity = true,
        fund = 0,
        homecam = true,
        modelName = "",
        phoneId = 0,
        rentalDeliveryLocation = "",
        rentalEnd = "",
        rentalId = 0,
        rentalStart = "",
        rentalStatus = 0,
        waybillNumber = "",
        y2K = true
    )
}