package com.ssafy.phonesin.model

data class ErrorResponse(
    val timestamp: String,
    val status: String,
    val error: String,
    val path: String
)

data class MessageResponse(
    val message: String,
    val status: Int
)