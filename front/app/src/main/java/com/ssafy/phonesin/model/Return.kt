package com.ssafy.phonesin.model

data class Return(
    var backDeliveryDate: String,
    var backDeliveryLocation: String,
    var backDeliveryLocationType: String,
    val phoneStatus: Boolean,
    val rentalId: Int,
    var review: String
)
