package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

data class User(
    val email: String,
    val memberName: String,
    val phoneNumber: String,
    val isCha: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class UserResponse(
    @SerializedName("member")
    val member: User,
    val message: String,
    val status: Int
)

data class UserModify(
    val isCha: Boolean,
    val memberName: String,
    val phoneNumber: String
) {
    constructor() : this(
        false,
        "",
        ""
    )
}

data class UserDonation(
    val donationId: Int,
    val memberId: Int,
    val donationStatus: Int,
    val donationCreatedAt: String,
    val donationDeliveryDate: String,
    val donationDeliveryLocationType: String,
    val donationDeliveryLocation: String,
    var toggle: Boolean
)

data class UserDonationResponse(
    val donation : List<UserDonation>
)

