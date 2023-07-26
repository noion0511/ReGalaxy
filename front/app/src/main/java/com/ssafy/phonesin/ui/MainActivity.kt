package com.ssafy.phonesin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNav()
    }

    private fun setNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()

        navController?.let {
            binding.navigationMain.setupWithNavController(it)
        }
    }
}