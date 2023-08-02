package com.ssafy.phonesin.ui.mobile.returnmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityReturnMobileBinding

class ReturnMobileActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnMobileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReturnMobile()
    }

    private fun setReturnMobile() {
        binding.buttonReturnNext.setOnClickListener {
            val intent: Intent = if (binding.radioButtonAgent.isChecked) {
                Intent(this, ReturnVisitDeliveryActivity::class.java)
            } else {
                Intent(this, ReturnAgentActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}