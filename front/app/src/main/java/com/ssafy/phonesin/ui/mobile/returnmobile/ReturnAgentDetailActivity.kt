package com.ssafy.phonesin.ui.mobile.returnmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityReturnAgentDetailBinding

class ReturnAgentDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnAgentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnAgentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReturnAgentDetailUi()
    }

    private fun setReturnAgentDetailUi() {
        binding.buttonPostReturnAgent.setOnClickListener {
            startActivity(Intent(this, ReturnFinishActivity::class.java))
            binding.root.removeView(binding.mapViewReturnAgentDetail)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}