package com.ssafy.phonesin.ui.login

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences.initJwtToken
import com.ssafy.phonesin.databinding.FragmentLoginBinding
import com.ssafy.phonesin.model.dto.LoginRequestDto
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {
    private val viewModel by activityViewModels<LoginViewModel>()

    private fun setLogInUi() {
        bindingNonNull.textViewSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        bindingNonNull.textViewNoIdMessage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        setLogInUi()
        initObserver()
        initLoginButton()
        initSignupButton()
    }

    private fun initLoginButton() {
        bindingNonNull.layoutLogIn.setDebouncingClickListener {
            val email = bindingNonNull.editTextLogInEmail.text.toString()
            val password = bindingNonNull.editTextLogInPassword.text.toString()

            if (viewModel.checkValidation(email, password)) {
                viewModel.login(LoginRequestDto(email, password))
            }
        }
    }


    private fun initSignupButton() {
        bindingNonNull.textViewSignUp.setDebouncingClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }


    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            token.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    initJwtToken(it)
                    findNavController().navigate(R.id.action_loginFragment_to_home)
                }
            }
        }
    }
}