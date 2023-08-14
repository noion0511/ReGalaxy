package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

data class Rank(
    val memberName: String,
    val donationCount: Int,
)

data class DonationRank(
    val donation: List<Rank>,
    val message: String,
    val status: Int
)

data class DonationCount(
    @SerializedName("donation phone cnt")
    val cnt: Int,
    val message: String,
    val status: Int
)