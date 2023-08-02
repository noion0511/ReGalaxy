package com.ssafy.phonesin.ui.mypage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.my.setOnClickListener {
            showModifyInfoDialog()
        }
    }

    private fun showModifyInfoDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_my_modify_info)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        dialog.window?.setBackgroundDrawable(shape)

        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}