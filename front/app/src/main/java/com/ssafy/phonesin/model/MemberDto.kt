package com.ssafy.phonesin.model

import com.google.gson.annotations.SerializedName

data class MemberDto(
    @SerializedName("email") val email: String,
    @SerializedName("memberName") val memberName: String,
    @SerializedName("password") val password: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)

data class SignUpInformation(
    val email: String,
    val emailCheck: Boolean,
    val memberName: String,
    val password: String,
    val passwordCheck: String,
    val phoneNumber: String
)

enum class MemberValidation {
    INVALID_EMAIL_FORMAT,
    PASSWORD_MISMATCH,
    SHORT_PASSWORD,
    EMPTY_EMAIL,
    EMAIL_NOT_VERIFIED
}