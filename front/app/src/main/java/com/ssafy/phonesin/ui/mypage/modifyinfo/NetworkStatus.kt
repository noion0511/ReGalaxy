package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.content.Context
import android.net.ConnectivityManager


object NetworkStatus {
    const val TYPE_WIFI = 1
    const val TYPE_MOBILE = 2
    const val TYPE_NOT_CONNECTED = 3
    fun getConnectivityStatus(context: Context): Int {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo != null) {
            val type = networkInfo.type
            if (type == ConnectivityManager.TYPE_MOBILE) { //모바일 (LTE, 5G)
                return TYPE_MOBILE
            } else if (type == ConnectivityManager.TYPE_WIFI) { //WIFI
                return TYPE_WIFI
            }
        }
        return TYPE_NOT_CONNECTED //연결 X
    }
}