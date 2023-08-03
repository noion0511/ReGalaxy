package com.ssafy.phonesin.ui.module.camera

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentCameraBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding>(
    R.layout.fragment_camera
), SurfaceHolder.Callback {

    private val viewModel by activityViewModels<CameraViewModel>()

    private lateinit var camera: Camera
    private lateinit var surfaceHolder: SurfaceHolder
    private var isSafeToTakePicture = false

    private var isFlashOn = false
    private lateinit var params: Camera.Parameters

    private var photoCount = 0
    private val maxPhotos = 4
    private var photoPaths = ArrayList<String>()
    private var cameraFacing = ArrayList<String>()

    private var cameraId = Camera.CameraInfo.CAMERA_FACING_BACK
    private val pictureCallback = Camera.PictureCallback { data, _ ->
        val photoPath = savePictureToPublicDir(data)
        val cameraFaceType =
            if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) "FRONT" else "BACK"
        photoPaths.add(photoPath)
        cameraFacing.add(cameraFaceType)
        if (photoCount < maxPhotos) {
            restartPreview()
        } else {
            photoCount = 0
            bindingNonNull.buttonTakePicture.isEnabled = true
        }
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraBinding {
        return FragmentCameraBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CameraFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        if (checkCameraHardware(requireContext())) {
            initCamera()
        } else {
            requireActivity().finish()
        }

        surfaceHolder = bindingNonNull.surfaceViewCamera.holder
        surfaceHolder.addCallback(this)

        bindingNonNull.textViewCount.text = "1 / 4"

        bindingNonNull.buttonTakePicture.setOnClickListener {
            if (isSafeToTakePicture) {
                bindingNonNull.textViewCount.text = "1 / 4"
                startCountdownAndTakePicture()
            }
        }

        bindingNonNull.buttonChangeView.setOnClickListener {
            changeCamera()
        }

        bindingNonNull.buttonTurnLight.setOnClickListener {
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

    private fun startCountdownAndTakePicture() {
        bindingNonNull.buttonTakePicture.visibility = View.INVISIBLE

        object : CountDownTimer(15000, 3000) { // 총 12초 동안 3초마다
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                bindingNonNull.textViewCount.text = "${(5 - (secondsRemaining) / 3)} / 4"
                takePicture()
            }

            override fun onFinish() {
                bindingNonNull.buttonTakePicture.visibility = View.VISIBLE
                val bundle = Bundle().apply {
                    putStringArrayList("photo_paths", photoPaths)
                    putStringArrayList("cameraFacing", cameraFacing)
                }
                findNavController().navigate(R.id.action_cameraFragment_to_cameraViewerFragment, bundle)
            }
        }.start()
    }

    private fun startCameraPreview() {
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
            isSafeToTakePicture = true
        } catch (e: Exception) {
            Log.d("CameraFragment", "Failed to start camera preview: ${e.message}")
        }
    }

    private val shutterCallback = Camera.ShutterCallback {}
    private fun takePicture() {
        if (isSafeToTakePicture) {
            camera.takePicture(shutterCallback, null, pictureCallback)
            isSafeToTakePicture = false
            photoCount++
        }
    }

    override fun onResume() {
        super.onResume()

        if (checkCameraHardware(requireContext())) {
            initCamera()

            surfaceHolder = bindingNonNull.surfaceViewCamera.holder
            surfaceHolder.addCallback(this)

            try {
                camera.setPreviewDisplay(surfaceHolder)
                camera.startPreview()
                isSafeToTakePicture = true
            } catch (e: Exception) {
                Log.d("CameraFragment", "Failed to start camera preview: ${e.message}")
            }

        } else {
            requireActivity().finish()
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

    private fun restartPreview() {
        camera.startPreview()
        isSafeToTakePicture = true
        bindingNonNull.buttonTakePicture.isEnabled = true
    }

    private fun savePictureToPublicDir(data: ByteArray): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, "IMG_$timeStamp.jpg")

        try {
            val fos = FileOutputStream(imageFile)
            fos.write(data)

            fos.close()

            requireContext().sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    imageFile.toUri()
                )
            )
            Log.d("tag", "사진 저장 ${imageFile.toUri()}")
        } catch (e: Exception) {
            showToast("사진 저장 실패")
        }

        return imageFile.absolutePath
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
            isSafeToTakePicture = true
        } catch (e: Exception) {
            Log.d("CameraFragment", "surfaceCreated: callback exception")
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera.release()
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
