package com.ssafy.phonesin

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.ssafy.phonesin.common.AppPreferences
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ApplicationClass : MultiDexApplication() {
    companion object {
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = AppPreferences.openSharedPreferences(applicationContext)
    }
}