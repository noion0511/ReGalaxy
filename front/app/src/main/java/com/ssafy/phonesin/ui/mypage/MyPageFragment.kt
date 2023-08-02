package com.ssafy.phonesin.ui.mypage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageBinding
import com.ssafy.phonesin.ui.MainActivity

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        textViewModifyInfo.setOnClickListener {
            showModifyInfoDialog()
        }

        textViewWithdrawal.setOnClickListener {
            showWithdrawalDialog()
        }

        textViewLogout.setOnClickListener {
            showLogoutDialog()
        }

        textViewNotificationSetting.setOnClickListener {
            findNavController().navigate(R.id.notificationFragment)
        }

    }

    private fun showLogoutDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_my_logout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(shape)



        dialog.show()
    }

    private fun showWithdrawalDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.diolog_my_withdrawal)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(shape)



        dialog.show()
    }

    private fun showModifyInfoDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_my_modify_info)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(shape)

        dialog.findViewById<TextView>(R.id.textViewRegistCha).setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.registChaFragment)
        }

        dialog.show()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}