package com.ssafy.phonesin.model.mypage

data class MyRental(
    var status: Int,
    var phoneNmae: String
)

data class MyRentalToggle(
    var rental: MyRental,
    var toggle: Boolean
)
