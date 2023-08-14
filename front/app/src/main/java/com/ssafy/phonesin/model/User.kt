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

data class UserRental(
    var toggle: Boolean,
    val rentalId: Int,
    val applyDate: String,
    val rentalStart: String,
    val rentalEnd: String,
    val rentalStatus: Int,
    val rentalDeliveryLocation: String,
    val fund: Int,
    val phoneId: Int,
    val modelName: String,
    val waybillNumber: String,
    val climateHumidity: Boolean,
    val homecam: Boolean,
    val y2K: Boolean
) {
    constructor() : this(
        false,
        applyDate = "",
        climateHumidity = true,
        fund = 0,
        homecam = true,
        modelName = "",
        phoneId = 0,
        rentalDeliveryLocation = "",
        rentalEnd = "",
        rentalId = 0,
        rentalStart = "",
        rentalStatus = 0,
        waybillNumber = "",
        y2K = true
    )
}

data class UserRentalResponse(
    @SerializedName("rental list")
    val rentalList: List<UserRental>,
    val message: String,
    val status: Int
)

data class UserReturn(
    var toggle: Boolean,
    val backId: Int,
    val phoneId: Int,
    val modelName: String,
    val backStatus: Int,
    val backDeliveryDate: String,
    val createdAt: String,
    val backDeliveryLocation: String,
    val phoneStatus: Boolean
)

data class UserReturnResponse(
    @SerializedName("data")
    val returnList: List<UserReturn>,
    val message: String,
    val status: Int
)
