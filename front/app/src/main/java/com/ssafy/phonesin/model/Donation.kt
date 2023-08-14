package com.ssafy.phonesin.model

data class Donation(
    var donationDeliveryDate: String,
    var donationDeliveryLocation: String,
    var donationDeliveryLocationType: String,
) {
    constructor() : this( "", "", "")
}