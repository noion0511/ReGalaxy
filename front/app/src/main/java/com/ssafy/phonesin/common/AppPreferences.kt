package com.ssafy.phonesin.common

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.phonesin.ApplicationClass.Companion.prefs

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val SHOWED_ON_BOARDING = "showed_onboarding"

    fun openSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun checkOnBoardingShowed() {
        prefs.edit().putBoolean(SHOWED_ON_BOARDING, true).apply()
    }

    fun isOnBoardingShowed() = prefs.getBoolean(SHOWED_ON_BOARDING, false)
}