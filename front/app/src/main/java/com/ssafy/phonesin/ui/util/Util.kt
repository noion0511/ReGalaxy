package com.ssafy.phonesin.ui.util

import com.ssafy.phonesin.model.Rental

object Util {
    fun selectModule(rental: Rental): String {
        var string = ""
        if (rental.y2K)
            string += "사진 ,"
        if (rental.homecam)
            string += "홈캠 ,"
        if (rental.climateHumidity)
            string += "온도계"
        return string
    }
}