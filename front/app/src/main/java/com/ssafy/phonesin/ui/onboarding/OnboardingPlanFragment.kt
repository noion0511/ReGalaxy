package com.ssafy.phonesin.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.ssafy.phonesin.databinding.FragmentOnboardingPlanBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class OnboardingPlanFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingPlanBinding
    private lateinit var mainActivity: MainActivity

    override fun onResume() {
        super.onResume()
        mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
        mainActivity.setFrameLayoutPaddingVerticle(binding.frameLayoutOnboardingPlan)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        buttonNext.setDebouncingClickListener {
            mainActivity.setNav()
        }
    }
}