package com.ssafy.phonesin.ui.module.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentFrameBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

private const val TAG = "FrameFragment"

@AndroidEntryPoint
class FrameFragment : BaseFragment<FragmentFrameBinding>(
    R.layout.fragment_frame
) {

    private val viewModel by activityViewModels<CameraViewModel>()
    private lateinit var photoPathStrings: List<String>
    private var selectedFrameColorInt by Delegates.notNull<Int>()

    private val colors = listOf(
        R.color.cameraFrame1,
        R.color.keyColorDark1,
        R.color.keyColorLight1
    )

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFrameBinding {
        return FragmentFrameBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FrameFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        initObserver()

        bindingNonNull.buttonNextQR.setOnClickListener {
            findNavController().navigate(R.id.action_frameFragment_to_QRCodeFragment)
        }

        bindingNonNull.viewFrame.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                uploadImage()

                bindingNonNull.viewFrame.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }
    }


    private fun showImage(photoPaths: List<String>) {
        for (i in photoPaths.indices) {
            val bitmap = BitmapFactory.decodeFile(photoPaths[i])

            val rotationDegrees = 90f
            val matrix = Matrix().apply { postRotate(rotationDegrees) }

            val rotatedBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            when (i) {
                0 -> bindingNonNull.photoViewer1.imageViewContent.setImageBitmap(rotatedBitmap)
                1 -> bindingNonNull.photoViewer2.imageViewContent.setImageBitmap(rotatedBitmap)
                2 -> bindingNonNull.photoViewer3.imageViewContent.setImageBitmap(rotatedBitmap)
                3 -> bindingNonNull.photoViewer4.imageViewContent.setImageBitmap(rotatedBitmap)
            }
        }
    }

    private fun setFrame(frame: Int) {
        bindingNonNull.photoViewer1.imageViewFrame.setBackgroundResource(frame)
        bindingNonNull.photoViewer2.imageViewFrame.setBackgroundResource(frame)
        bindingNonNull.photoViewer3.imageViewFrame.setBackgroundResource(frame)
        bindingNonNull.photoViewer4.imageViewFrame.setBackgroundResource(frame)
    }

    private fun convertColorToFrame(color: Int): Int {
        return when (color) {
            R.color.keyColorDark1 -> R.drawable.round_corners_key_dark_color
            R.color.keyColorLight1 -> R.drawable.round_corners_key_light_color
            R.color.cameraFrame1 -> R.drawable.round_corners_frame
            else -> R.drawable.round_corners_frame
        }
    }

    private fun layoutToBitmap(layout: View): Bitmap? {
        layout.isDrawingCacheEnabled = true
        layout.buildDrawingCache()
        val drawingCache = layout.drawingCache
        if (drawingCache != null) {
            val bitmap = Bitmap.createBitmap(drawingCache)
            layout.isDrawingCacheEnabled = false
            return bitmap
        }
        layout.isDrawingCacheEnabled = false
        return null
    }


    private fun uploadImage() {
        Log.d(TAG, "uploadImage: ")
        val frameBitmap = layoutToBitmap(bindingNonNull.rootView)

        frameBitmap?.let {
            val timeStamp =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile =
                File(storageDir, "IMG_${timeStamp}_${viewModel.getPrintCountFromPrefs()+1}.jpeg").apply {
                    parentFile?.let {
                        if (!it.exists()) it.mkdirs()
                }
            }
            try {
                val fos = FileOutputStream(imageFile)
                frameBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()

                requireContext().sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        imageFile.toUri()
                    )
                )
                Log.d("FrameFragment", "사진 저장 ${imageFile.toUri()}")
            } catch (e: Exception) {
                Log.d("FrameFragment", "사진 저장 실패 ${imageFile.toUri()}")
            } finally {
                viewModel.uploadImage(imageFile)
                bindingNonNull.buttonNextQR.visibility = View.VISIBLE
            }
            frameBitmap.recycle()
        }
    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            selectedImagePaths.observe(viewLifecycleOwner) {
                photoPathStrings = it
                Log.d(TAG, "photoPaths: $photoPathStrings")
                if (photoPathStrings.size == 4) {
                    showImage(photoPathStrings)
                    bindingNonNull.photoViewer.cardView.visibility = View.INVISIBLE
                } else if (photoPathStrings.size == 1) {
                    val originalBitmap = BitmapFactory.decodeFile(photoPathStrings[0])

                    val rotationDegrees = 90f
                    val matrix = Matrix().apply { postRotate(rotationDegrees) }

                    val rotatedBitmap = Bitmap.createBitmap(
                        originalBitmap,
                        0,
                        0,
                        originalBitmap.width,
                        originalBitmap.height,
                        matrix,
                        true
                    )

                    bindingNonNull.photoViewer.imageViewContent.setImageBitmap(rotatedBitmap)
                    bindingNonNull.imageViewFour.visibility = View.INVISIBLE

                }
            }

            selectedFrameColor.observe(viewLifecycleOwner) {
                selectedFrameColorInt = it
                Log.d(TAG, "selectedFrameColorInt: $selectedFrameColorInt")
                if (it in colors) {
                    bindingNonNull.viewFrame.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            selectedFrameColorInt
                        )
                    )

                    bindingNonNull.photoViewer.imageViewFrame.setBackgroundResource(
                        convertColorToFrame(selectedFrameColorInt)
                    )

                    setFrame(convertColorToFrame(selectedFrameColorInt))

                    when (selectedFrameColorInt) {
                        R.color.cameraFrame1 -> {
                            bindingNonNull.imageViewFramePuppy.visibility = View.VISIBLE
                            bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                            bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                        }
                        R.color.keyColorDark1 -> {
                            bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                            bindingNonNull.imageViewFrameLogo.visibility = View.VISIBLE
                            bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                        }
                        else -> {
                            bindingNonNull.textViewTime.visibility = View.INVISIBLE
                            bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                            bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                            bindingNonNull.imageViewFrameLogo2.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}
