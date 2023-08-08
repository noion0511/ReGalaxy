package com.ssafy.phonesin.model

data class Donation(

    val donationCreatedAt: String,
    val donationDeliveryDate: String,
    val donationDeliveryLocation: String,
    val donationDeliveryLocationType: String,
    val donationId: Int,
    val donationStatus: Int,
    val memberId: Int
) {
    constructor() : this("", "", "", "", 0, 0, 0)
}