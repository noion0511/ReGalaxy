package com.ssafy.phonesin.ui.module.camera

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CameraPageAdapter(
    fragmentManager: FragmentManager,
    private val photoPaths: List<String>, private val cameraFace: List<String>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = photoPaths.size

    override fun getItem(position: Int): Fragment = PhotoFragment.newInstance(photoPaths[position], cameraFace[position])
}