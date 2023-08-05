package com.ssafy.phonesin.ui

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.phonesin.ApplicationClass
import com.ssafy.phonesin.ApplicationClass.Companion.prefs
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences
import com.ssafy.phonesin.databinding.ActivityMainBinding
import com.ssafy.phonesin.network.SSLConnect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3300L
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ssl = SSLConnect()
        ssl.postHttps("https://map.kakao.com/",1000,1000)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d("version", "onCreate: 젤리빈")
            setNav()
        } else {
            setSplash()
        }
    }

    private fun setSplash() {
        val navController = supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()
        navController?.navigate(R.id.splashFragment)

        window.decorView.postDelayed({
            if (!AppPreferences.isOnBoardingShowed()) {
//                AppPreferences.checkOnBoardingShowed()
                navController?.navigate(R.id.onboardingDonateFragment)
            } else {
                setNav()
            }
        }, SPLASH_DELAY)
    }



    fun setNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        navController?.navigate(R.id.home)

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }
    fun hideBottomNavi(state: Boolean){
        if(state) binding.navigationMain.visibility = View.GONE else binding.navigationMain.visibility = View.VISIBLE
    }
}