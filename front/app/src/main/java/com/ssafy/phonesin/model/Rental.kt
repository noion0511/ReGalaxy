package com.ssafy.phonesin.model

data class Rental(
    val applyDate: String,
    val climateHumidity: Boolean,
    val fund: Int,
    val homecam: Boolean,
    val modelName: String,
    val phoneId: Int,
    val rentalDeliveryLocation: String,
    val rentalEnd: String,
    val rentalId: Int,
    val rentalStart: String,
    val rentalStatus: Int,
    val waybillNumber: String,
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