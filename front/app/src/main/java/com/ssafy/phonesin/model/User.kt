package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

//data class Member(
//    @SerializedName("email") val email: String,
//    @SerializedName("memberName") val memberName: String,
//    @SerializedName("phoneNumber") val phoneNumber: String,
//    @SerializedName("isCha") val isCha: Boolean,
//    @SerializedName("createdAt") val createdAt: String,
//    @SerializedName("updatedAt") val updatedAt: String
//)
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