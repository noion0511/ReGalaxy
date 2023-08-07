package com.ssafy.phonesin.ui.login

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentLogInBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLogInBinding>(
    R.layout.fragment_login
) {
    private val viewModel by activityViewModels<LoginViewModel>()

    private fun setLogInUi() {
        bindingNonNull.textViewFindPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        bindingNonNull.textViewSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        bindingNonNull.textViewNoIdMessage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLogInBinding {
        return FragmentLogInBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }
    }

    override fun init() {
        setLogInUi()
    }
}