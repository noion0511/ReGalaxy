package com.ssafy.phonesin.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object AppPreferences {
    private const val LOGIN_SESSION = "login.session"
    private const val JWT_TOKEN = "jwt_token"

    private lateinit var preferences: SharedPreferences
    private val gson = GsonBuilder().create()

    fun openSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun initJwtToken(jwtToken: String) {
        preferences.edit().putString(JWT_TOKEN, jwtToken).commit()
    }

    fun getJwtToken(): String? {
        return preferences.getString(JWT_TOKEN, "")
    }

    fun removeJwtToken() {
        preferences.edit().remove(JWT_TOKEN).apply()
    }
}