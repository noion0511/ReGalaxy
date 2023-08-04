package com.ssafy.phonesin.ui.module.camera

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
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
    private lateinit var params: Camera.Parameters

    private var photoCount = 0
    private val maxPhotos = 4
    private var photoPaths = ArrayList<String>()

    private var cameraId = Camera.CameraInfo.CAMERA_FACING_BACK
    private val pictureCallback = Camera.PictureCallback { data, _ ->
        val photoPath = savePictureToPublicDir(data)
        photoPaths.add(photoPath)
        if (photoCount < maxPhotos) {
            restartPreview()
        } else {
            photoCount = 0
            bindingNonNull.buttonTakePicture.isEnabled = true
            bindingNonNull.progressBar.progress = 0
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

        bindingNonNull.progressBar.progress = 0

        bindingNonNull.buttonTakePicture.setOnClickListener {
            if (isSafeToTakePicture) {
                bindingNonNull.progressBar.progress = 0
                startCountdownAndTakePicture()
            }
        }

        initObserver()
    }

    private fun initCamera() {
        releaseCamera()
        camera = getCameraInstance(cameraId) ?: return
        camera.setDisplayOrientation(90)
        params = camera.parameters
    }

    private fun startCountdownAndTakePicture() {
        bindingNonNull.buttonTakePicture.visibility = View.INVISIBLE
        var allCountDown = 24

        object : CountDownTimer(
            25000,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                if (allCountDown < 0) return

                val countTime = (allCountDown % 6)
                bindingNonNull.textViewCountTime.visibility = if (countTime == 0) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
                bindingNonNull.textViewCountTime.text = countTime.toString()
                bindingNonNull.progressBar.progress += 1

                if (countTime == 0 && allCountDown != 24) {
                    takePicture()
                }
                allCountDown--
            }

            override fun onFinish() {
                viewModel.updatePhotoPaths(photoPaths)
                bindingNonNull.buttonTakePicture.visibility = View.VISIBLE
                photoPaths = ArrayList<String>()
            }
        }.start()
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

    private fun initObserver() {
        with(viewModel) {
            photoPaths.observe(viewLifecycleOwner) {
                if(it.isNotEmpty())
                    findNavController().navigate(R.id.action_cameraFragment_to_cameraViewerFragment)
            }
        }
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
