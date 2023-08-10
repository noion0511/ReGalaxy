package com.ssafy.phonesin.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSplash()

        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    REQUIRED_PERMISSIONS[0]
                )
            ) {
                // 사용자가 임시로 권한을 거부한 경우
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 처음 권한을 요청하거나 사용자가 '다시 묻지 않음'을 선택한 경우
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {
            var check_result = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                // 모든 권한이 허용된 경우
                // 권한이 허용되면 여기에 원하는 코드를 작성합니다.
            } else {
                // 권한이 거부된 경우
                // 사용자가 권한을 거부하면 여기에 원하는 코드를 작성합니다.
            }
        }
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
            if (!AppPreferences.isOnBoardingShowed()) {
                AppPreferences.checkOnBoardingShowed()
                navController?.navigate(R.id.onboardingDonateFragment)
            } else {
                navController?.navigate(R.id.module)
            }
        }, SPLASH_DELAY)
    }
}