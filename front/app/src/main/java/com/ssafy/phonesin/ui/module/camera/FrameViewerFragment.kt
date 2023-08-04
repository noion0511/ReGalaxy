package com.ssafy.phonesin.ui.module.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
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

@AndroidEntryPoint
class FrameViewerFragment : BaseFragment<FragmentFrameViewerBinding>(
    R.layout.fragment_frame_viewer
) {

    private val viewModel by activityViewModels<CameraViewModel>()

    private var colorIndex = 0
    private val colors = listOf(
        R.color.cameraFrame1,
        R.color.keyColorDark1,
        R.color.keyColorLight1
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

        val imagePath = arguments?.getString("imagePath") ?: ""
        val photoPaths = arguments?.getStringArrayList("photo_paths")

        if(photoPaths.isNullOrEmpty() && imagePath != null) {
            val originalBitmap = BitmapFactory.decodeFile(imagePath)
            Log.d("FrameFragment", "Original Bitmap: $originalBitmap")

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

            bindingNonNull.imageViewOne.photoViewer.setImageBitmap(rotatedBitmap)
            bindingNonNull.imageViewFour.visibility = View.INVISIBLE
        } else if(photoPaths != null && photoPaths.size == 4){
            showImage(photoPaths)
        }

        bindingNonNull.buttonChoicePicture.setOnClickListener {

            val bundle = Bundle().apply {
                putString("imagePath", imagePath)
                putStringArrayList("photo_paths", photoPaths)
                putInt("frameColor", colors[colorIndex])
            }

            findNavController().navigate(R.id.action_frameViewerFragment_to_frameFragment, bundle)
        }

        bindingNonNull.buttonArrowLeft.setOnClickListener {
            colorIndex = (colorIndex + 1) % colors.size
            bindingNonNull.frameView.setBackgroundColor(ContextCompat.getColor(requireContext(), colors[colorIndex]))
            if(colors[colorIndex] == R.color.cameraFrame1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE

            } else if(colors[colorIndex] == R.color.keyColorLight1){
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
            } else {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.VISIBLE
            }
        }

        bindingNonNull.buttonArrowRight.setOnClickListener {
            colorIndex = (colorIndex - 1 + colors.size) % colors.size
            bindingNonNull.frameView.setBackgroundColor(ContextCompat.getColor(requireContext(), colors[colorIndex]))
            if(colors[colorIndex] == R.color.cameraFrame1) {
                bindingNonNull.imageViewFramePuppy.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE

            } else if(colors[colorIndex] == R.color.keyColorDark1){
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.VISIBLE
                bindingNonNull.imageViewFrameLogo2.visibility = View.GONE
            } else {
                bindingNonNull.imageViewFramePuppy.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo.visibility = View.GONE
                bindingNonNull.imageViewFrameLogo2.visibility = View.VISIBLE
            }
        }
    }

    private fun showImage(photoPaths: List<String>) {
        for (i in photoPaths.indices) {
            val bitmap = BitmapFactory.decodeFile(photoPaths[i])

            val rotationDegrees = 90f
            val matrix = Matrix().apply { postRotate(rotationDegrees) }

            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            when (i) {
                0 -> bindingNonNull.photoViewer1.photoViewer.setImageBitmap(rotatedBitmap)
                1 -> bindingNonNull.photoViewer2.photoViewer.setImageBitmap(rotatedBitmap)
                2 -> bindingNonNull.photoViewer3.photoViewer.setImageBitmap(rotatedBitmap)
                3 -> bindingNonNull.photoViewer4.photoViewer.setImageBitmap(rotatedBitmap)
            }
        }

        bindingNonNull.imageViewOne.cardView.visibility = View.INVISIBLE
    }
}
