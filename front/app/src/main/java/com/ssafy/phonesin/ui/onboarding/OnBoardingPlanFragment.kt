package com.ssafy.phonesin.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentOnboardingPlanBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingPlanFragment : BaseFragment<FragmentOnboardingPlanBinding>(
    R.layout.fragment_onboarding_plan
) {
    private lateinit var mainActivity: MainActivity


    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingPlanBinding {
        return FragmentOnboardingPlanBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        mainActivity = activity as MainActivity
        mainActivity.setFrameLayoutPaddingVertical(bindingNonNull.frameLayoutOnboardingPlan)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {}
        setOnClick()
    }

    private fun setOnClick() = with(bindingNonNull) {
        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.module)
        }
    }
}