package com.ssafy.phonesin.ui.module.hygrometer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHomeCamBinding
import com.ssafy.phonesin.databinding.FragmentHygrometerBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HygrometerFragment : Fragment() {
    private lateinit var binding: FragmentHygrometerBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHygrometerBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HygrometerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}