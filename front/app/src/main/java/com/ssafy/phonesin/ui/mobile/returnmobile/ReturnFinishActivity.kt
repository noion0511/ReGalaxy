package com.ssafy.phonesin.ui.mobile.returnmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityReturnFinishBinding

class ReturnFinishActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReturnFinishUi()
    }

    private fun setReturnFinishUi() {
        binding.buttonReturnHome.setOnClickListener {
            finish()
        }
    }
}