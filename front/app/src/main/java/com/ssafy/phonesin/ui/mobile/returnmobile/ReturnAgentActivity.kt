package com.ssafy.phonesin.ui.mobile.returnmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityReturnAgentBinding

class ReturnAgentActivity : AppCompatActivity() {

    lateinit var binding: ActivityReturnAgentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnAgentBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setReturnAgentUI()
    }

    private fun setReturnAgentUI() {
        binding.buttonTemp.setOnClickListener {
            startActivity(
                Intent(this, ReturnAgentDetailActivity::class.java)
            )
            finish()
        }
    }
}