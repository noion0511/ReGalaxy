package com.ssafy.phonesin.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ActivityMainBinding
import com.ssafy.phonesin.ui.util.SSLConnect
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ssl = SSLConnect()
        ssl.postHttps("https://map.kakao.com/",1000,1000)

        setNav()
    }

    private fun setNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }
    fun hideBottomNavi(state: Boolean){
        if(state) binding.navigationMain.visibility = View.GONE else binding.navigationMain.visibility = View.VISIBLE
    }
}