package com.ssafy.phonesin.ui.signup

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentSignupBinding
import com.ssafy.phonesin.model.*
import com.ssafy.phonesin.model.dto.EmailRequestDto
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(
    R.layout.fragment_signup
) {
    private val viewModel by activityViewModels<SignupViewModel>()
    private var emailCheckStatue = false

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SignupFragment.viewModel
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)

        initObserver()

        bindingNonNull.editTextSignUpEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                emailCheckStatue = false
                bindingNonNull.textViewEmailExplain.text = ""
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 텍스트가 변경되기 전에 호출됩니다. 여기에서는 특별한 작업을 하지 않아도 됩니다.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경되는 동안에 호출됩니다. 여기에서는 특별한 작업을 하지 않아도 됩니다.
            }
        })
                bindingNonNull.buttonVerifyEmail.setDebouncingClickListener {
            val email = bindingNonNull.editTextSignUpEmail.text.toString()
            val password = bindingNonNull.editTextSignUpPassword.text.toString()
            val passwordCheck = bindingNonNull.editTextSignUpPasswordCheck.text.toString()
            val phoneNumber = bindingNonNull.editTextSignUpPhoneNumber.text.toString()
            val name = bindingNonNull.editTextSignUpName.text.toString()

            val signUpInfo = SignUpInformation(
                email = email,
                password = password,
                passwordCheck = passwordCheck,
                phoneNumber = phoneNumber,
                memberName = name,
                emailCheck = emailCheckStatue
            )

            val validateEmailMessage = viewModel.validateEmail(email)
            if (validateEmailMessage == EmailValidation.VALID_EMAIL_FORMAT) {
                viewModel.verifyEmail(EmailRequestDto(email))
                viewModel.setUserInputEmail(signUpInfo)
                findNavController().navigate(R.id.action_signupFragment_to_emailCheckFragment)
            } else {
                when (validateEmailMessage) {
                    EmailValidation.EMPTY_EMAIL -> bindingNonNull.textViewEmailExplain.text =
                        getString(R.string.signup_email_empty)

                    EmailValidation.INVALID_EMAIL_FORMAT -> bindingNonNull.textViewEmailExplain.text =
                        getString(R.string.signup_invalid_email_format)

                    else -> bindingNonNull.textViewEmailExplain.text =
                        getString(R.string.signup_invalid_email_format)
                }
            }
        }

        bindingNonNull.buttonSigunUp.setDebouncingClickListener {
            if(!emailCheckStatue) {
                showToast("이메일 인증을 완료해주세요.")
                return@setDebouncingClickListener
            }

            val email = bindingNonNull.editTextSignUpEmail.text.toString()
            val password = bindingNonNull.editTextSignUpPassword.text.toString()
            val passwordCheck = bindingNonNull.editTextSignUpPasswordCheck.text.toString()
            val phoneNumber = bindingNonNull.editTextSignUpPhoneNumber.text.toString()
            val name = bindingNonNull.editTextSignUpName.text.toString()

            val signUpInfo = SignUpInformation(
                email = email,
                password = password,
                passwordCheck = passwordCheck,
                phoneNumber = phoneNumber,
                memberName = name,
                emailCheck = emailCheckStatue
            )

            val errors = viewModel.validateMember(signUpInfo)

            if (errors.isEmpty()) {
                viewModel.signup(MemberDto(email, name, password, phoneNumber))
            } else {
                for (error in errors) {
                    when (error) {
                        MemberValidation.EMPTY_EMAIL -> {
                            bindingNonNull.textViewEmailExplain.text =
                                getString(R.string.signup_email_empty)
                        }

                        MemberValidation.INVALID_EMAIL_FORMAT -> {
                            bindingNonNull.textViewEmailExplain.text =
                                getString(R.string.signup_invalid_email_format)

                        }

                        MemberValidation.SHORT_PASSWORD -> {
                            bindingNonNull.textViewSignUpPasswordExplain.text =
                                getString(R.string.signup_short_password)

                        }

                        MemberValidation.PASSWORD_MISMATCH -> {
                            bindingNonNull.textViewSignUpPasswordExplain.text =
                                getString(R.string.signup_password_mismatch)

                        }

                        MemberValidation.EMAIL_NOT_VERIFIED -> {
                            bindingNonNull.textViewEmailExplain.text =
                                getString(R.string.signup_email_not_verified)

                        }

                        MemberValidation.NO_NAME -> {
                            bindingNonNull.textViewSignUpNameExplain.text = "이름을 입력해 주세요."
                        }
                    }
                }
            }
        }
    }

    private fun initObserver() {
        with(viewModel) {
            errorMsg.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    showToast(it)
                }
            }

            emailConfirmStatus.observe(viewLifecycleOwner) {
                if (it == ConfirmEmail.OK) {
                    emailCheckStatue = true
                    bindingNonNull.textViewEmailExplain.text =
                        getString(R.string.signup_email_confirm_ok)
                    bindingNonNull.editTextSignUpEmail.setText(memberDto.value?.email.toString())
                } else if (it == ConfirmEmail.EMAIL_EXISTS) {
                    bindingNonNull.textViewEmailExplain.text =
                        getString(R.string.signup_exists_email)
                }
            }

            signupResponse.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let {
                    if (it.status.toInt() == 200) {
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    }
                }
            }

            memberDto.observe(viewLifecycleOwner) {
                if (it.email.isNotBlank()) {
                    bindingNonNull.editTextSignUpEmail.setText(it.email)
                    bindingNonNull.editTextSignUpName.setText(it.memberName)
                    bindingNonNull.editTextSignUpPhoneNumber.setText(it.phoneNumber)
                }
            }
        }
    }
}