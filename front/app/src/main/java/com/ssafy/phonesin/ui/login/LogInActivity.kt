package com.ssafy.phonesin.ui.login

import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setLogInUi()
    }

    private fun setLogInUi() {
        binding.textViewFindPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textViewSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textViewNoIdMessage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }
}