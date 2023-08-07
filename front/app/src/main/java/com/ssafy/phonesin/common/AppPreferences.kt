package com.ssafy.phonesin.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.ssafy.phonesin.ApplicationClass.Companion.prefs
import com.ssafy.phonesin.model.Token

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val JWT_ACCESS_TOKEN = "jwt_access_token"
    private const val JWT_REFRESH_TOKEN = "jwt_refresh_token"

    private val gson = GsonBuilder().create()

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
}