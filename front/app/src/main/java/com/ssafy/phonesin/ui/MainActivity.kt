package com.ssafy.phonesin.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
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
//        try {
//            val information =
//                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                information.signingInfo.apkContentsSigners
//            } else {
//                TODO("VERSION.SDK_INT < P")
//            }
//            val md = MessageDigest.getInstance("SHA")
//            for (signature in signatures) {
//                val md: MessageDigest
//                md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                var hashcode = String(Base64.encode(md.digest(), 0))
//                Log.d("hashcode", "" + hashcode)
//            }
//        } catch (e: Exception) {
//            Log.d("hashcode", "에러::" + e.toString())
//
//        }
        setNav()
    }

    private fun setNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }
}