package com.ssafy.phonesin.ui.mypage.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageNotificationBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentMyPageNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchNotification.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.keyColor1)
                switchNotification.trackTintList = ContextCompat.getColorStateList(requireContext(), R.color.keyColorLight1)
            } else {
                switchNotification.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.unselectedLightColor)
                switchNotification.trackTintList = ContextCompat.getColorStateList(requireContext(), R.color.unselectedColor)
            }
        }

        buttonSaveNotification.setDebouncingClickListener {
            findNavController().navigate(R.id.my_page)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}