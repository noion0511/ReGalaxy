package com.ssafy.phonesin.model

data class ErrorResponse(
    val status: String,
    val message: String
)

data class MessageResponse(
    val message: String,
    val status: Int
)