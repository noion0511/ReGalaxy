package com.ssafy.phonesin.model

data class Climate(
    val address: String,
    val weather: String,
    val humidity: Int,
    val climate: Double,
    val message: String,
    val status: Int
)

data class Location(
    val latitude: Double,
    val longitude: Double
)