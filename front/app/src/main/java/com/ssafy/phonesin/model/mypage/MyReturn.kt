package com.ssafy.phonesin.model.mypage

data class MyReturn(
    var status: Int,
    var phoneNmae: String
)

data class MyReturnToggle(
    var donate: MyReturn,
    var toggle: Boolean
)
