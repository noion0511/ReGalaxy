package com.ssafy.phonesin.model

data class Rank(
    val memberName: String,
    val donationCount: Int,
)

data class DonationRank(
    val donation: List<Rank>,
    val message: String,
    val status: Int
)