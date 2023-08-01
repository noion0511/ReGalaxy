package com.ssafy.phonesin.ui.home

import HomeRankAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ssafy.phonesin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var rankRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBanner()
        setRank()
    }

    private fun setBanner() {
        viewPager = binding.viewPagerBanner
        indicator = binding.indicatorBanner

        indicator.setupWithViewPager(viewPager)
        viewPager.adapter = HomeViewPagerAdapter(childFragmentManager)

        for (i in 0 until indicator.getTabCount()) {
            val tab = (indicator.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as MarginLayoutParams
            p.setMargins(0, 0, 10, 0)
            tab.requestLayout()
        }
    }

    private fun setRank() {
        rankRecyclerView = binding.recyclerViewRank

        val rankList = listOf<Int>(1,2,3,4,5)
        binding.recyclerViewRank.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewRank.adapter = HomeRankAdapter(rankList)

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