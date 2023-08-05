package com.ssafy.phonesin.ui.module.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentFrameViewerBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FrameViewerFragment"
@AndroidEntryPoint
class FrameViewerFragment : BaseFragment<FragmentFrameViewerBinding>(
    R.layout.fragment_frame_viewer
) {

    private val viewModel by activityViewModels<CameraViewModel>()
    private lateinit var photoPathStrings: List<String>
    private lateinit var selectedOnePhotoPathString: String

    private var colorIndex = 0
    private val colors = listOf(
        R.color.cameraFrame1,
        R.color.keyColorDark1,
        R.color.keyColorLight1
    )
    private val frames = listOf(
        R.drawable.round_corners_frame,
        R.drawable.round_corners_key_dark_color,
        R.drawable.round_corners_key_light_color
    )

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFrameViewerBinding {
        return FragmentFrameViewerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FrameViewerFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        bindingNonNull.buttonChoicePicture.setOnClickListener {
            viewModel.setSelectedFrameColor(colors[colorIndex])
        }

        bindingNonNull.buttonArrowLeft.setOnClickListener {
            colorIndex = (colorIndex + 1) % colors.size
            bindingNonNull.frameView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    colors[colorIndex]
                )
            )
            setFrameColor(frames[colorIndex])
            if (colors[colorIndex] == R.color.cameraFrame1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_frame)

            } else if (colors[colorIndex] == R.color.keyColorLight1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_key_light_color)
            } else {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.VISIBLE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_key_dark_color)
            }
        }

        bindingNonNull.buttonArrowRight.setOnClickListener {
            colorIndex = (colorIndex - 1 + colors.size) % colors.size
            bindingNonNull.frameView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    colors[colorIndex]
                )
            )
            setFrameColor(frames[colorIndex])
            if (colors[colorIndex] == R.color.cameraFrame1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_frame)
            } else if (colors[colorIndex] == R.color.keyColorDark1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_key_dark_color)

            } else {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.VISIBLE
                bindingNonNull.imageViewOne.imageViewFrame.setBackgroundResource(R.drawable.round_corners_key_light_color)
            }
        }

        initObserver()
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

    private fun setFrameColor(color: Int) {
        bindingNonNull.photoViewer1.imageViewFrame.setBackgroundResource(color)
        bindingNonNull.photoViewer2.imageViewFrame.setBackgroundResource(color)
        bindingNonNull.photoViewer3.imageViewFrame.setBackgroundResource(color)
        bindingNonNull.photoViewer4.imageViewFrame.setBackgroundResource(color)
    }

    private fun initObserver() {
        with(viewModel) {
            photoPaths.observe(viewLifecycleOwner) {
                photoPathStrings = it
                Log.d(TAG, "photoPathStrings: $photoPathStrings")

                if (photoPathStrings.size == 4) {
                    showImage(photoPathStrings)
                }
            }

            selectedImagePath.observe(viewLifecycleOwner) {
                selectedOnePhotoPathString = it
                Log.d(TAG, "selectedOnePhotoPathString: $selectedOnePhotoPathString")
                if (selectedOnePhotoPathString.isNotEmpty()) {
                    val originalBitmap = BitmapFactory.decodeFile(selectedOnePhotoPathString)

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

                    bindingNonNull.imageViewOne.imageViewContent.setImageBitmap(rotatedBitmap)
                    bindingNonNull.imageViewFour.visibility = View.GONE
                }
            }

            selectedFrameColor.observe(viewLifecycleOwner) {
                if (it in colors)
                    findNavController().navigate(R.id.action_frameViewerFragment_to_frameFragment)
            }
        }
    }
}
