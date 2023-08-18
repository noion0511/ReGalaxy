package com.ssafy.phonesin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHomeBannerBinding

class HomeBannerFragment() : Fragment() {
    private lateinit var binding: FragmentHomeBannerBinding
    private lateinit var layout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = binding.layoutBanner

        val index = arguments?.getInt(ARG_INDEX) ?: 0

        if(index == 0) {
            layout.setBackgroundResource(R.drawable.banner1)
        } else if (index == 1) {
            layout.setBackgroundResource(R.drawable.banner2)
        } else {
            layout.setBackgroundResource(R.drawable.banner3)
        }
    }

    companion object {
        private const val ARG_INDEX = "index"

        @JvmStatic
        fun newInstance(index: Int) =
            HomeBannerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                }
            }
    }
}