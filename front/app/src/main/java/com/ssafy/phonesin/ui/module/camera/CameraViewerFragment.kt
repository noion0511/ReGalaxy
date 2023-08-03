package com.ssafy.phonesin.ui.module.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentCameraViewerBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator

@AndroidEntryPoint
class CameraViewerFragment : BaseFragment<FragmentCameraViewerBinding>(
    R.layout.fragment_camera_viewer
) {

    private val viewModel by activityViewModels<CameraViewModel>()

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CameraPageAdapter

    private var colorIndex = 0
    private val colors = listOf(
        R.color.keyColorDark1,
        R.color.keyColorLight1,
        R.color.keyColorLight2
    )

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraViewerBinding {
        return FragmentCameraViewerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CameraViewerFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        val photoPaths = arguments?.getStringArrayList("photo_paths") ?: emptyList<String>()

        viewPager = bindingNonNull.viewPagerPhotoViewer
        pagerAdapter = CameraPageAdapter(childFragmentManager, photoPaths)
        viewPager.adapter = pagerAdapter

        val indicator: CircleIndicator = bindingNonNull.indicator
        indicator.setViewPager(viewPager)

        bindingNonNull.buttonChoicePicture.setOnClickListener {
            val currentImagePosition = viewPager.currentItem
            val currentImagePath = photoPaths[currentImagePosition]

            val bundle = Bundle().apply {
                putString("imagePath", currentImagePath)
            }

            findNavController().navigate(R.id.action_cameraViewerFragment_to_frameViewerFragment, bundle)
        }

        bindingNonNull.buttonArrowLeft.setOnClickListener {
            viewPager.currentItem = (viewPager.currentItem - 1) % pagerAdapter.count
        }

        bindingNonNull.buttonArrowRight.setOnClickListener {
            viewPager.currentItem = (viewPager.currentItem + 1) % pagerAdapter.count
        }
    }
}
