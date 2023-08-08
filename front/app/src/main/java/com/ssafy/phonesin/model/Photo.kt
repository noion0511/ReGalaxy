package com.ssafy.phonesin.model

data class Photo(
    val ytwokId: Int,
    val originalFile: String,
    val saveFile: String
)

data class PhotoResponse(
    val message: String,
    val status: String,
    val photos: Photo
)