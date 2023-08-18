package com.ssafy.phonesin.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ssafy.phonesin.ApplicationClass.Companion.prefs
import com.ssafy.phonesin.model.Hygrometer
import com.ssafy.phonesin.model.Token
import java.util.Date

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val JWT_ACCESS_TOKEN = "jwt_access_token"
    private const val JWT_REFRESH_TOKEN = "jwt_refresh_token"
    private const val SHOWED_ON_BOARDING = "showed_onboarding"
    private const val LAST_HYGROMETER = "last_hygrometer"
    private const val LAST_HYGROMETER_DATE = "last_hygrometer_date"
    private const val NOTIFICATION_SETTING = "notification_setting"

    private val gson = Gson()

    fun openSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun initJwtToken(jwtToken: Token) {
        prefs.edit().putString(JWT_ACCESS_TOKEN, jwtToken.accessToken).putString(JWT_REFRESH_TOKEN, jwtToken.refreshToken).apply()
    }

    fun getAccessToken(): String {
        return prefs.getString(JWT_ACCESS_TOKEN, "")!!
    }

    fun getRefreshToken(): String {
        return prefs.getString(JWT_REFRESH_TOKEN, "")!!
    }

    fun removeJwtToken() {
        prefs.edit().remove(JWT_ACCESS_TOKEN).remove(JWT_REFRESH_TOKEN).apply()
    }

    fun checkOnBoardingShowed() {
        prefs.edit().putBoolean(SHOWED_ON_BOARDING, true).apply()
    }

    fun isOnBoardingShowed() = prefs.getBoolean(SHOWED_ON_BOARDING, false)

    fun getHygrometerList() : List<Hygrometer> {
        val savedJson = prefs.getString(LAST_HYGROMETER, null)

        if (savedJson != null) {
            val type = object : TypeToken<List<Hygrometer>>() {}.type
            return gson.fromJson(savedJson, type)
        } else {
            return emptyList()
        }
    }

    fun saveHygrometerList(list: List<Hygrometer>) {
        val json = gson.toJson(list)
        prefs.edit().putString(LAST_HYGROMETER, json).apply()
    }

    fun checkLastHygrometerDate(date: String) : Boolean {
        val lastDate = prefs.getString(LAST_HYGROMETER_DATE, "")
        if (lastDate == date) return true
        else return false
    }

    fun setLastHygrometerDate(date: String) {
        prefs.edit().putString(LAST_HYGROMETER_DATE, date).apply()
    }

    fun getNotificationSetting() : Boolean = prefs.getBoolean(NOTIFICATION_SETTING, false)

    fun setNotificationSetting(setting: Boolean) {
        prefs.edit().putBoolean(NOTIFICATION_SETTING, setting).apply()
    }
}