package com.ssafy.phonesin.ui.mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.phonesin.databinding.ActivityRentalListBinding

class RentalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRentalListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRentalListUi()
    }

    private fun setRentalListUi() {
        binding.mobileAdd.setOnClickListener {
            startActivity(Intent(this, AddMobileActivity::class.java))
        }
        binding.postRental.setOnClickListener {
            startActivity(Intent(this, PayMobileActivity::class.java))
        }
    }
}