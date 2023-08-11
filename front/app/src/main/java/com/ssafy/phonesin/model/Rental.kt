package com.ssafy.phonesin.model

data class Rental(
    var toggle: Boolean,
    val rentalId: Int,
    val applyDate: String,
    val rentalStart: String,
    val rentalEnd: String,
    val rentalStatus: Int,
    val rentalDeliveryLocation: String,
    val fund: Int,
    val phoneId: Int,
    val modelName: String,
    val waybillNumber: String,
    val climateHumidity: Boolean,
    val homecam: Boolean,
    val y2K: Boolean
) {
    constructor() : this(
        false,
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