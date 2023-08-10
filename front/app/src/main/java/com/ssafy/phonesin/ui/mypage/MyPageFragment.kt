package com.ssafy.phonesin.ui.mypage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences
import com.ssafy.phonesin.databinding.FragmentMyPageBinding
import com.ssafy.phonesin.ui.MainActivity

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    val userViewModel: UserViewModel by activityViewModels()

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
        layoutRental.setOnClickListener {
            findNavController().navigate(R.id.rentalListFragment)
        }

        layoutReturn.setOnClickListener {
            findNavController().navigate(R.id.returnListFragment)
        }

        layoutDonate.setOnClickListener {
            findNavController().navigate(R.id.donateListFragment)
        }

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
        val logoutDialog = Dialog(requireContext())
        setDialogDefault(logoutDialog, R.layout.dialog_my_logout)

        logoutDialog.findViewById<TextView>(R.id.buttonCancel).setOnClickListener {
            logoutDialog.dismiss()
        }

        logoutDialog.findViewById<Button>(R.id.buttonLogout).setOnClickListener {
//            logoutDialog.dismiss()
//            AppPreferences.removeJwtToken()
//            Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
////            val mainActivity = activity as MainActivity
////            mainActivity.hideBottomNavi(false)
////            mainActivity.setNav()
//            findNavController().navigate(R.id.action_my_page_to_loginFragment)
        }

        logoutDialog.show()
    }

    private fun showWithdrawalDialog() {
        val withdrawalDialog = Dialog(requireContext())

        setDialogDefault(withdrawalDialog, R.layout.diolog_my_withdrawal)

        withdrawalDialog.findViewById<TextView>(R.id.buttonCancel).setOnClickListener {
            withdrawalDialog.dismiss()
        }

        withdrawalDialog.findViewById<Button>(R.id.buttonWithdrawal).setOnClickListener {
            userViewModel.withdrawal()
            withdrawalDialog.dismiss()
            Toast.makeText(requireContext(), "회원 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

        withdrawalDialog.show()
    }

    private fun showModifyInfoDialog() {
        val modifyInfoDialog = Dialog(requireContext())

        setDialogDefault(modifyInfoDialog, R.layout.dialog_my_modify_info)

        modifyInfoDialog.findViewById<TextView>(R.id.textViewRegistCha).setOnClickListener {
            modifyInfoDialog.dismiss()
            findNavController().navigate(R.id.registChaFragment)
        }

        modifyInfoDialog.findViewById<TextView>(R.id.textViewModifyInfo).setOnClickListener {
            modifyInfoDialog.dismiss()
            findNavController().navigate(R.id.modifyInfoFragment)
        }

        modifyInfoDialog.show()
    }

    private fun setDialogDefault(dialog: Dialog, layout: Int) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        dialog.setContentView(layout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(shape)
    }
}