package com.ssafy.phonesin.ui.home

import HomeRankAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ssafy.phonesin.databinding.FragmentHomeBinding
import com.ssafy.phonesin.ui.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var rankRecyclerView: RecyclerView

    val homeViewModel: HomeViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
        setBanner()
        setRank()
        setDonationInfo()
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

        homeViewModel.getRank()
        rankRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rankRecyclerView.adapter = HomeRankAdapter(homeViewModel.donationRank)

        homeViewModel.donationRank.observe(viewLifecycleOwner, Observer { donationRank ->
            rankRecyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun setDonationInfo() {
        homeViewModel.getDonationCount()
        homeViewModel.donationCnt.observe(viewLifecycleOwner, Observer { donationCnt ->
            binding.textViewDonationCnt.text = "총 ${donationCnt}대 기증"
        })
    }
}