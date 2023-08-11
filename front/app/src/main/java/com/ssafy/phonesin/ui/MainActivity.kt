package com.ssafy.phonesin.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.Manifest
import android.content.pm.PackageManager
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences
import com.ssafy.phonesin.databinding.ActivityMainBinding
import com.ssafy.phonesin.network.SSLConnect
import com.ssafy.phonesin.ui.module.camera.CameraFragment
import com.ssafy.phonesin.ui.module.camera.CameraXFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3300L
    private lateinit var binding: ActivityMainBinding

    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ssl = SSLConnect()
        ssl.postHttps("https://map.kakao.com/", 1000, 1000)

        setStatusBarTransparent()
//        setNav()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Log.d("version", "onCreate: 젤리빈")
            setNav()
        } else {
            setSplash()
        }

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                // 사용자가 임시로 권한을 거부한 경우
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            } else {
                // 처음 권한을 요청하거나 사용자가 '다시 묻지 않음'을 선택한 경우
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerMain) as NavHostFragment
                val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment

                if (currentFragment is CameraFragment) {
                    currentFragment.clickedTakePictureButton()
                    return true
                } else if(currentFragment is CameraXFragment) {
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
//                AppPreferences.checkOnBoardingShowed()
                navController?.navigate(R.id.onboardingDonateFragment)
            } else {
                setNav()
            }
        }, SPLASH_DELAY)
    }


    fun setNav() {
        setPadding()
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        navController?.navigate(R.id.loginFragment)

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }

    fun hideBottomNavi(state: Boolean) {
        if (state) binding.navigationMain.visibility =
            View.GONE else binding.navigationMain.visibility = View.VISIBLE
    }

    fun setCameraFrameLayoutPaddingVerticle(layout: ConstraintLayout) {
        binding.containerMain.setPadding(0, 0, 0, 0)
        binding.mainActivityLayout.setPadding(0, 0, 0, 0)
        layout.setPadding(0, 0, 0, navigationHeight())
    }

    fun setFrameLayoutPaddingVerticle(layout: FrameLayout) {
        layout.setPadding(0, statusBarHeight(), 0, navigationHeight())
    }

    fun setPadding() {
        binding.containerMain.setPadding(0, statusBarHeight(), 0, 0)
        binding.mainActivityLayout.setPadding(0, 0, 0, navigationHeight())
    }

    private fun setStatusBarTransparent() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if (Build.VERSION.SDK_INT >= 30) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun statusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else 0
    }

    private fun navigationHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId <= 0 || Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) 0
        else resources.getDimensionPixelSize(resourceId)
    }
}