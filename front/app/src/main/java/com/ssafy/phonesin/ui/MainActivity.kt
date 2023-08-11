package com.ssafy.phonesin.ui

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences
import com.ssafy.phonesin.databinding.ActivityMainBinding
import com.ssafy.phonesin.ui.module.camera.CameraXFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3300L
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSplash()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP,
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.containerMain) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment

                if (currentFragment is CameraXFragment) {
                    currentFragment.clickedRemoteTakePictureButton()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setSplash() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()
        navController?.navigate(R.id.splashFragment)

        window.decorView.postDelayed({
            if (!AppPreferences.isOnBoardingShowed() && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                AppPreferences.checkOnBoardingShowed()
                navController?.navigate(R.id.onboardingDonateFragment)
            } else {
                navController?.navigate(R.id.module)
            }
        }, SPLASH_DELAY)
    }
}