package com.ssafy.phonesin.ui.module.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentQRCodeBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class QRCodeFragment : BaseFragment<FragmentQRCodeBinding>(
    R.layout.fragment_q_r_code
) {
    private val viewModel by activityViewModels<CameraViewModel>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        Glide.with(requireContext())
            .load("https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl=http://i9d102.p.ssafy.io:8080/ytwok/images/${viewModel.getSelectedImageId()}")
            .override(200, 200)
            .centerCrop()
            .into(bindingNonNull.imageViewQRCode)

        bindingNonNull.buttonCameraNext.setOnClickListener {
            findNavController().navigate(R.id.action_QRCodeFragment_to_cameraFragment)
        }

        bindingNonNull.buttonPrint.setOnClickListener {
            viewModel.increasePrintCount()
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("출력 신청 완료")
            .setMessage("출력 신청이 완료되었습니다. ${viewModel.printCount.value}번 번호에 신청되었습니다.")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
        bindingNonNull.buttonPrint.visibility = View.INVISIBLE
    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QRCodeFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}