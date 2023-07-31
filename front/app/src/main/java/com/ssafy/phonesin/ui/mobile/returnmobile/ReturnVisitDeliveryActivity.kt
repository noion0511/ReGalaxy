package com.ssafy.phonesin.ui.mobile.returnmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityReturnVisitDeliveryBinding

class ReturnVisitDeliveryActivity : AppCompatActivity() {
    lateinit var binding: ActivityReturnVisitDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnVisitDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReturnVisitDeliveryUi()
    }

    private fun setReturnVisitDeliveryUi() {
        binding.buttonPostReturnVisitDelivery.setOnClickListener {
            startActivity(Intent(this, ReturnFinishActivity::class.java))
            finish()
        }
    }
}