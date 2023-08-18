package com.ssafy.phonesin.ui.module.camera

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentQRCodeBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QRCodeFragment : BaseFragment<FragmentQRCodeBinding>(
    R.layout.fragment_q_r_code
) {
    private val viewModel by activityViewModels<CameraViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQRCodeBinding {
        return FragmentQRCodeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@QRCodeFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
        initObserver()

        bindingNonNull.buttonCameraNext.setDebouncingClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                findNavController().navigate(R.id.action_QRCodeFragment_to_cameraXFragment)
            } else {
                findNavController().navigate(R.id.action_QRCodeFragment_to_cameraFragment)
            }
        }

        bindingNonNull.buttonPrint.setDebouncingClickListener {
            viewModel.increasePrintCount()
            showDialog()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                findNavController().navigate(R.id.action_QRCodeFragment_to_cameraXFragment)
            } else {
                findNavController().navigate(R.id.action_QRCodeFragment_to_cameraFragment)
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("출력 신청 완료")
            .setMessage("출력 신청이 완료되었습니다. ${viewModel.getPrintCountFromPrefs()}번 번호에 신청되었습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
        bindingNonNull.buttonPrint.visibility = View.INVISIBLE
    }

    private fun initObserver() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bindingNonNull.LinearLayoutLoading.visibility = View.GONE
        }

        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            photoResponse.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it.message == getString(R.string.success)) {
                        Glide.with(requireContext())
                            .load("https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl=http://i9d102.p.ssafy.io:8080/ytwok/images/${it.photos.saveFile}")
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        bindingNonNull.LinearLayoutLoading.visibility = View.GONE
                                    }, 2000)
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        bindingNonNull.LinearLayoutLoading.visibility = View.GONE
                                    }, 2000)
                                    return false
                                }
                            })
                            .override(200, 200)
                            .centerCrop()
                            .into(bindingNonNull.imageViewQRCode)

                        viewModel.setSelectedImagePaths(emptyList())
                        viewModel.updatePhotoPaths(emptyList())
                        viewModel.setSelectedFrameColor(-1)
                    }
                }
            }
        }
    }
}