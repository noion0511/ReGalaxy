package com.ssafy.phonesin.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.phonesin.R
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
            navController?.navigate(R.id.home)
            setNav()
        }, SPLASH_DELAY)
    }

    private fun setNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        hideBottomNavi(false)

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }
    fun hideBottomNavi(state: Boolean){
        if(state) binding.navigationMain.visibility = View.GONE else binding.navigationMain.visibility = View.VISIBLE
    }
}