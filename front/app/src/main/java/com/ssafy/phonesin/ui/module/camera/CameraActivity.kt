package com.ssafy.phonesin.ui.module.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.net.toUri
import com.ssafy.phonesin.databinding.ActivityCameraBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : Activity() {
    private lateinit var binding: ActivityCameraBinding

    private lateinit var camera: Camera
    private lateinit var surfaceHolder: SurfaceHolder
    private var isSafeToTakePicture = false

    private var isFlashOn = false
    private lateinit var params: Camera.Parameters

    private var cameraId = Camera.CameraInfo.CAMERA_FACING_BACK
    private val pictureCallback = Camera.PictureCallback { data, _ ->
        savePictureToPublicDir(data)
        restartPreview()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkCameraHardware(this)) {
            initCamera()
        } else {
            finish()
        }

        surfaceHolder = binding.surfaceViewCamera.holder
        surfaceHolder.addCallback(surfaceHolderCallback)

        binding.buttonTakePicture.setOnClickListener {
            if (isSafeToTakePicture) {
                startCountdownAndTakePicture()
            }
        }

        binding.buttonChangeView.setOnClickListener {
            changeCamera()
        }

        binding.buttonTurnLight.setOnClickListener {
            toggleFlash()
        }
    }

    private fun initCamera() {
        releaseCamera()
        camera = getCameraInstance(cameraId) ?: return
        camera.setDisplayOrientation(90)
        params = camera.parameters
    }

    private fun changeCamera() {
        // 카메라 ID를 변경합니다. 후면이면 전면으로, 전면이면 후면으로 변경합니다.
        cameraId = if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            Camera.CameraInfo.CAMERA_FACING_FRONT
        } else {
            Camera.CameraInfo.CAMERA_FACING_BACK
        }
        initCamera() // 카메라를 다시 초기화합니다.
        startCameraPreview() // 카메라 프리뷰를 시작합니다.
    }

    private fun toggleFlash() {
        if (isFlashOn) {
            params.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera.parameters = params
            isFlashOn = false
        } else {
            params.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera.parameters = params
            isFlashOn = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startCountdownAndTakePicture() {
        binding.buttonTakePicture.isEnabled = false

        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                showToast((secondsRemaining + 1).toString())
            }

            override fun onFinish() {
                takePicture()
            }
        }.start()
    }

    private fun startCameraPreview() {
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
            isSafeToTakePicture = true
        } catch (e: Exception) {
            Log.d("CameraActivity", "Failed to start camera preview: ${e.message}")
        }
    }
    private val shutterCallback = Camera.ShutterCallback { }
    private fun takePicture() {
        if (isSafeToTakePicture) {
            camera.takePicture(shutterCallback, null, pictureCallback)
            isSafeToTakePicture = false
        }
    }

    override fun onResume() {
        super.onResume()

        if (checkCameraHardware(this)) {
            initCamera()

            surfaceHolder = binding.surfaceViewCamera.holder
            surfaceHolder.addCallback(surfaceHolderCallback)

            try {
                camera.setPreviewDisplay(surfaceHolder)
                camera.startPreview()
                isSafeToTakePicture = true
            } catch (e: Exception) {
                Log.d("CameraActivity", "Failed to start camera preview: ${e.message}")
            }

        } else {
            finish()
        }
    }

    private fun releaseCamera() {
        if (::camera.isInitialized) {
            camera.release() // 카메라 인스턴스를 해제합니다.
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
    }


    private val surfaceHolderCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                camera.setPreviewDisplay(holder)
                camera.startPreview()
                isSafeToTakePicture = true
            } catch (e: Exception) {
                Log.d("surfaceHolderCallback", "surfaceCreated: callback exception")
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            camera.release()
        }
    }

    private fun restartPreview() {
        camera.startPreview()
        isSafeToTakePicture = true
        binding.buttonTakePicture.isEnabled = true
    }

    private fun savePictureToPublicDir(data: ByteArray) {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, "IMG_$timeStamp.jpg")

        try {
            val fos = FileOutputStream(imageFile)
            fos.write(data)
            fos.close()

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageFile.toUri()))
            Log.d("tag", "사진 저장 ${imageFile.toUri()}")
        } catch (e: Exception) {
            showToast("사진 저장 실패")
        } finally {
            val intent = Intent(this, CameraViewerActivity::class.java)
            intent.putExtra("photo_path", imageFile.absolutePath)
            startActivity(intent)
        }
    }

    companion object {
        fun checkCameraHardware(context: Context): Boolean {
            return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
        }

        fun getCameraInstance(cameraId: Int): Camera? {
            return try {
                Camera.open(cameraId)
            } catch (e: Exception) {
                null
            }
        }
    }
}
