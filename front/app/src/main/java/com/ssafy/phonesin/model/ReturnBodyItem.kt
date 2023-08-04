package com.ssafy.phonesin.model

data class ReturnBodyItem(
    val backDeliveryDate: String,
    val backDeliveryLocation: String,
    val backDeliveryLocationType: String,
    val backId: Int,
    val backStatus: Int,
    val backZipcode: String,
    val createdAt: String,
    val rentalId: Int,
    val review: String
)