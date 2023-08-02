package com.ssafy.phonesin.model

data class Photo(
    val ytwokId: Int,
    val originalFile: String,
    val saveFile: String
)

data class PhotoResponse(
    override val message: String,
    val photos: List<Photo>
) : BaseResponse(message)