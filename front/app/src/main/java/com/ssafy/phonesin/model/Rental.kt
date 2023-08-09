package com.ssafy.phonesin.model

data class Rental(
    val climateHumidity: Boolean,
    var count: Int,
    val donationId: Int,
    val fund: Int,
    val homecam: Boolean,
    val memberId: Int,
    val phoneId: Int,
    val rentalDeliveryLocation: String,
    val usingDate: Int,
    val y2K: Boolean
) {
    constructor() : this(
        climateHumidity = false,
        count = 0,
        donationId = 0,
        fund = 0,
        homecam = false,
        memberId = 0,
        phoneId = 0,
        rentalDeliveryLocation = "",
        usingDate = 0,
        y2K = false
    )
}