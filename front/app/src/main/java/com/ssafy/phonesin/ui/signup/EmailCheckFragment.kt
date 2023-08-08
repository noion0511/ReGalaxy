package com.ssafy.phonesin.ui.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentEmailCheckBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(
    R.layout.fragment_email_check
) {
    private val viewModel by activityViewModels<SignupViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailCheckBinding {
        return FragmentEmailCheckBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@EmailCheckFragment.viewModel
        }
    }

    override fun init() {
        initObserver()
        initSignupButton()
    }


    private fun initSignupButton(){

    }


    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }
        }
    }
}