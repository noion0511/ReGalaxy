package com.ssafy.phonesin.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentSplashBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    R.layout.fragment_splash
) {
    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
    }
}