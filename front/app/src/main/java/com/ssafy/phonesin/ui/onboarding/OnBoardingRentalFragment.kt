package com.ssafy.phonesin.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentOnboardingRentalBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingRentalFragment : BaseFragment<FragmentOnboardingRentalBinding>(
    R.layout.fragment_onboarding_rental
) {
    private lateinit var mainActivity: MainActivity

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingRentalBinding {
        return FragmentOnboardingRentalBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        mainActivity = activity as MainActivity
        mainActivity.setFrameLayoutPaddingVertical(bindingNonNull.frameLayoutOnboardingRental)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        setOnClick()
    }

    private fun setOnClick() = with(bindingNonNull) {
        textViewSkip.setOnClickListener {
            findNavController().navigate(R.id.module)
        }

        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.onboardingModuleFragment)
        }
    }
}