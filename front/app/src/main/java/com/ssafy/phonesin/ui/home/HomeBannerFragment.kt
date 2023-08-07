package com.ssafy.phonesin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHomeBannerBinding
import com.ssafy.phonesin.databinding.FragmentHomeBinding

class HomeBannerFragment(val index : Int) : Fragment() {
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

        if(index == 0) {
            layout.setBackgroundResource(R.drawable.banner1)
        } else if (index == 1) {
            layout.setBackgroundResource(R.drawable.banner2)
        } else {
            layout.setBackgroundResource(R.drawable.banner3)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}