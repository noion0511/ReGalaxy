package com.ssafy.phonesin.ui.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentEmailCheckBinding
import com.ssafy.phonesin.model.ConfirmEmail
import com.ssafy.phonesin.model.dto.EmailCheckRequestDto
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(
    R.layout.fragment_email_check
) {
    private val viewModel by activityViewModels<SignupViewModel>()
    private lateinit var memberEmail: String

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
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        initObserver()
        initPostButton()
        initConfirmButton()
    }

    private fun initPostButton() {
        bindingNonNull.textViewPostNumber.setDebouncingClickListener {
            viewModel.verifyEmail(EmailRequestDto(memberEmail))
        }
    }

    private fun initConfirmButton() {
        bindingNonNull.textViewEmailConfirm.setDebouncingClickListener {
            val verifyNumber = bindingNonNull.editTextEmailCheck.text.toString()
            if (verifyNumber.isBlank()) {
                showToast(getString(R.string.signup_email_confirm_number))
                return@setDebouncingClickListener
            }
            viewModel.verifyEmailConfirm(EmailCheckRequestDto(memberEmail, verifyNumber))
        }
    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            emailCheck.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    when (it) {
                        getString(R.string.signup_email_verify_success) -> {
                            viewModel.setEmailConfirmStatus(Pair(ConfirmEmail.OK, memberEmail))
                            findNavController().navigate(R.id.action_emailCheckFragment_to_signupFragment)
                        }
                        getString(R.string.signup_exists_email) -> {
                            viewModel.setEmailConfirmStatus(Pair(ConfirmEmail.EMAIL_EXISTS, memberEmail))
                        }
                        else -> {
                            showToast(it)
                        }
                    }
                }
            }

            memberDto.observe(viewLifecycleOwner) {
                memberEmail = it.email
                bindingNonNull.TextViewEmailCheckExplain.text =
                    getString(R.string.signup_email_verify_explain, memberEmail)
            }
        }
    }
}