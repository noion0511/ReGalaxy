package com.ssafy.phonesin.ui.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        Log.d("ViewPager", "Creating fragment at position $position")
        return HomeBannerFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 3
    }
}