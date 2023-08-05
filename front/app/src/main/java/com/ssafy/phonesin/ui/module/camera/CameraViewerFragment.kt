package com.ssafy.phonesin.ui.module.camera

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

@AndroidEntryPoint
class CameraViewerFragment : BaseFragment<FragmentCameraViewerBinding>(
    R.layout.fragment_camera_viewer
) {

    private val viewModel by activityViewModels<CameraViewModel>()

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CameraPageAdapter
    private lateinit var photoPathStrings: List<String>

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

        viewPager = bindingNonNull.viewPagerPhotoViewer
        pagerAdapter = CameraPageAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        bindingNonNull.buttonChoicePicture.setOnClickListener {
            val currentImagePosition = viewPager.currentItem
            val currentImagePath = photoPathStrings[currentImagePosition]
            viewModel.setSelectedImagePath(currentImagePath)
        }

        bindingNonNull.textAllChoice.setOnClickListener {
            findNavController().navigate(R.id.action_cameraViewerFragment_to_frameViewerFragment)
        }

        bindingNonNull.buttonArrowLeft.setOnClickListener {
            if (viewPager.currentItem == 0) viewPager.currentItem = pagerAdapter.count - 1
            else viewPager.currentItem = (viewPager.currentItem - 1) % pagerAdapter.count

        }

        bindingNonNull.buttonArrowRight.setOnClickListener {
            viewPager.currentItem = (viewPager.currentItem + 1) % pagerAdapter.count
        }

        initObserver()
    }

    private fun initObserver() {
        with(viewModel) {
            photoPaths.observe(viewLifecycleOwner) {
                photoPathStrings = it
                pagerAdapter.setPhotoPath(it)
                bindingNonNull.indicator.setViewPager(viewPager)
            }

            selectedImagePath.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty())
                    findNavController().navigate(R.id.action_cameraViewerFragment_to_frameViewerFragment)
            }
        }
    }
}
