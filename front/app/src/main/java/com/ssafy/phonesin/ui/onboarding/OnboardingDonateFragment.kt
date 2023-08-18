package com.ssafy.phonesin.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentOnboardingDonateBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class OnboardingDonateFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingDonateBinding
    private lateinit var mainActivity: MainActivity

    override fun onResume() {
        super.onResume()
        mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
        mainActivity.setFrameLayoutPaddingVerticle(binding.frameLayoutOnboardingDonate)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingDonateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        textViewSkip.setDebouncingClickListener {
            mainActivity.setNav()
        }

        buttonNext.setDebouncingClickListener {
            findNavController().navigate(R.id.onboardingRentalFragment)
        }
    }
}