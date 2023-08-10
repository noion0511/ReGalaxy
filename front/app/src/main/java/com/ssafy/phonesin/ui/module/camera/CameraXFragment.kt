package com.ssafy.phonesin.ui.module.camera

import android.media.MediaActionSound
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentCameraXBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CameraXFragment"

@AndroidEntryPoint
class CameraXFragment : BaseFragment<FragmentCameraXBinding>(R.layout.fragment_camera_x),
    LifecycleOwner {
    private lateinit var cameraProvider: ProcessCameraProvider
    private var imageCapture: ImageCapture? = null

    private val viewModel by activityViewModels<CameraViewModel>()
    private val sound = MediaActionSound()

    private var photoCount = 0
    private val maxPhotos = 4
    private var photoPaths = ArrayList<String>()
    private var cameraState = 0

    private var countDownTimer: CountDownTimer? = null
    private val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraXBinding {
        return FragmentCameraXBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CameraXFragment.viewModel
        }
    }

    override fun init() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .setTargetRotation(bindingNonNull.viewFinder.display.rotation)
                .build()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(bindingNonNull.viewFinder.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        bindingNonNull.progressBar.progress = 0

        bindingNonNull.buttonTakePicture.setOnClickListener {
            bindingNonNull.progressBar.progress = 0
            startCountdownAndTakePicture()
        }

        initObserver()
    }

    private fun startCountdownAndTakePicture() {
        bindingNonNull.textViewCountPicture.text = "$photoCount / $maxPhotos"
        cameraState = 2
        bindingNonNull.buttonTakePicture.visibility = View.INVISIBLE
        var allCountDown = 24

        object : CountDownTimer(
            26000,
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

            }
        }.start()
    }

    private fun remoteTakePicture() {
        bindingNonNull.textViewCountPicture.text = "$photoCount / $maxPhotos"
        bindingNonNull.buttonTakePicture.visibility = View.INVISIBLE
        bindingNonNull.textViewCountTime.visibility = View.INVISIBLE

        var allCountDown = 24

        countDownTimer = object : CountDownTimer(
            26000,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                if (allCountDown < 0) return

                bindingNonNull.textViewCountTime.text = allCountDown.toString()
                bindingNonNull.progressBar.progress += 1

                if (allCountDown == 12 && photoCount == 0) {
                    bindingNonNull.textViewCountTime.visibility = View.VISIBLE
                    takePicture()
                } else if (allCountDown == 9 && photoCount == 1) {
                    bindingNonNull.textViewCountTime.visibility = View.VISIBLE
                    takePicture()
                } else if (allCountDown == 6 && photoCount == 2) {
                    bindingNonNull.textViewCountTime.visibility = View.VISIBLE
                    takePicture()
                } else if (allCountDown == 0 && photoCount == 3) {
                    bindingNonNull.textViewCountTime.visibility = View.VISIBLE
                    takePicture()
                }

                allCountDown--
            }

            override fun onFinish() {
                viewModel.updatePhotoPaths(photoPaths)
                photoPaths = ArrayList<String>()
            }
        }.start()
    }

    fun clickedRemoteTakePictureButton() {
        if (cameraState == 0) {
            remoteTakePicture()
            cameraState = 1
        } else if (cameraState == 1) {
            takePicture()
        }
    }

    private fun takePicture() {
        sound.play(MediaActionSound.SHUTTER_CLICK)

        val file = getOutputDirectory()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture?.takePicture(outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(file).toString()
                    val modifiedPhotoPath = savedUri.replace("file://", "")
                    Log.d(TAG, "onImageSaved: $savedUri")
                    photoPaths.add(modifiedPhotoPath)
                    photoCount++
                    bindingNonNull.textViewCountPicture.text = "$photoCount / $maxPhotos"

                    if (photoCount >= maxPhotos) {
                        viewModel.updatePhotoPaths(photoPaths)
                        photoPaths = ArrayList<String>()
                        countDownTimer?.cancel()
                        return
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            })
    }

    private fun initObserver() {
        with(viewModel) {
            photoPaths.observe(viewLifecycleOwner) {
                if (it.isNotEmpty())
                    findNavController().navigate(R.id.action_cameraXFragment_to_cameraViewerFragment)
            }
        }
    }

    private fun getOutputDirectory(): File {
        val filename = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, "$filename.jpg").apply {
            parentFile?.let {
                if (!it.exists()) it.mkdirs()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sound.release()
        if (::cameraProvider.isInitialized) {
            cameraProvider.unbindAll()
        }
    }
}
