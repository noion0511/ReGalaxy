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
