package com.ssafy.phonesin.model

data class Address(
    val address: String,
    val addressId: Int
)

data class AddressToggle(
    val addressDto: Address,
    val toggle: Boolean
)