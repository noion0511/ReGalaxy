package com.ssafy.phonesin.model

data class AgentAddress(
    val agencyLocation: String,
    val agencyName: String,
    val agencyPhoneNum: String,
    val agencyPhotoUrl: String,
    val agencyX: Double,
    val agencyY: Double,
    val distance: Double
)