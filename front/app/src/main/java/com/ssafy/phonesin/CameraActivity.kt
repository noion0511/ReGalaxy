package com.ssafy.phonesin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
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

    private val pictureCallback = Camera.PictureCallback { data, _ ->
        savePictureToPublicDir(data)
        restartPreview()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)

        if (checkCameraHardware(this)) {
            initCamera()
        } else {
            // Handle the case where the device doesn't have a camera
            finish()
        }

        surfaceHolder = binding.svCamera.holder
        surfaceHolder.addCallback(surfaceHolderCallback)

        binding.btnTakePicture.setOnClickListener {
            if (isSafeToTakePicture) {
                startCountdownAndTakePicture()
            }
        }
    }

    private fun initCamera() {
        camera = getCameraInstance() ?: return
        camera.setDisplayOrientation(90)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startCountdownAndTakePicture() {
        binding.btnTakePicture.isEnabled = false

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

    private fun takePicture() {
        if (isSafeToTakePicture) {
            camera.takePicture(null, null, pictureCallback)
            isSafeToTakePicture = false
        }
    }

    private val surfaceHolderCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                camera.setPreviewDisplay(holder)
                camera.startPreview()
                isSafeToTakePicture = true
            } catch (e: Exception) {
                // Handle the error
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // If your camera can support preview size changes, handle it here
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // Release the camera resources when the surface is destroyed
            camera.release()
        }
    }

    private fun restartPreview() {
        camera.startPreview()
        isSafeToTakePicture = true
        binding.btnTakePicture.isEnabled = true
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
        }
    }

    companion object {
        fun checkCameraHardware(context: Context): Boolean {
            return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
        }

        fun getCameraInstance(): Camera? {
            return try {
                Camera.open()
            } catch (e: Exception) {
                null
            }
        }
    }
}
