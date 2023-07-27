package com.ssafy.phonesin.ui.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityFinishMobileBinding

class FinishMobileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishMobileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFinishMobileUi()
    }

    private fun setFinishMobileUi() {
        binding.buttonMobileHome.setOnClickListener {
            finish()
        }
    }
}