package com.ssafy.phonesin.ui.util

import com.ssafy.phonesin.model.RentalBody
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Util {
    fun selectModule(rental: RentalBody): String {
        var string = ""
        if (rental.y2K)
            string += "사진, "
        if (rental.homecam)
            string += "홈캠, "
        if (rental.climateHumidity)
            string += "온도계, "

        return string.dropLast(2)
    }

    fun getCurrentKoreaTime(): String {
        val koreaTimeZone = "Asia/Seoul"
        val koreaLocale = Locale("ko", "KR")
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm", koreaLocale)
        sdf.timeZone = TimeZone.getTimeZone(koreaTimeZone)
        return sdf.format(
            Calendar.getInstance(
                TimeZone.getTimeZone(koreaTimeZone),
                koreaLocale
            ).time
        )
    }

    fun convertCalendarToDate(time: Long): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dateFormat.format(Date(time))
    }

    fun convertToDate(year: Int, month: Int, day: Int): String {
        //val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return "$year.$month.$day"
    }
}