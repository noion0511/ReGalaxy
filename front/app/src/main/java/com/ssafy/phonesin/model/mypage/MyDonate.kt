package com.ssafy.phonesin.model.mypage

data class MyDonate(
    var status: Int,
    var donateDate: String
)

data class MyDonateToggle(
    var donate: MyDonate,
    var toggle: Boolean
)