package com.ssafy.phonesin.model

data class Return(
    var backDeliveryDate: String,
    var backDeliveryLocation: String,
    var backDeliveryLocationType: String,
    val backId: Int,
    val backStatus: Int,
    val createdAt: String,
    val phoneId: Int,
    val phoneStatus: Boolean,
    val rentalId: Int,
    var review: String
)