package com.ssafy.phonesin.ui.module.camera

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
        R.color.keyColorDark1,
        R.color.keyColorLight1,
        R.color.keyColorLight2
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

        bindingNonNull.imageView.setImageBitmap(rotatedBitmap)

        bindingNonNull.buttonChoicePicture.setOnClickListener {

            val bundle = Bundle().apply {
                putString("imagePath", imagePath)
                putInt("frameColor", colors[colorIndex])
            }

            findNavController().navigate(R.id.action_frameViewerFragment_to_frameFragment, bundle)
        }

        bindingNonNull.buttonArrowLeft.setOnClickListener {
            colorIndex = (colorIndex + 1) % colors.size
            bindingNonNull.frameView.setBackgroundColor(ContextCompat.getColor(requireContext(), colors[colorIndex]))
        }

        bindingNonNull.buttonArrowRight.setOnClickListener {
            colorIndex = (colorIndex - 1 + colors.size) % colors.size
            bindingNonNull.frameView.setBackgroundColor(ContextCompat.getColor(requireContext(), colors[colorIndex]))
        }
    }
}
