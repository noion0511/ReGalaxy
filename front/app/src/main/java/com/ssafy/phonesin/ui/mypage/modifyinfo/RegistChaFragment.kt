package com.ssafy.phonesin.ui.mypage.modifyinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageRegistChaBinding
import com.ssafy.phonesin.ui.MainActivity

class RegistChaFragment : Fragment() {
    private lateinit var binding: FragmentMyPageRegistChaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageRegistChaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() = with(binding) {

        buttonSaveCha.setOnClickListener {
            findNavController().navigate(R.id.my_page)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistChaFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}