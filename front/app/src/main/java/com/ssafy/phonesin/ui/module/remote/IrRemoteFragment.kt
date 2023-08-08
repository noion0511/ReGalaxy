package com.ssafy.phonesin.ui.module.remote

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssafy.phonesin.R

class IrRemoteFragment : Fragment() {

    private lateinit var irManager: ConsumerIrManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ir_remote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Toast.makeText(activity, "Your device does not support IR transmission.", Toast.LENGTH_SHORT).show()
            return
        }

        irManager = activity?.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        if (!irManager.hasIrEmitter()) {
            Toast.makeText(activity, "No IR emitter found on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        val sendIrButton: Button = view.findViewById(R.id.sendIrButton)
        sendIrButton.setOnClickListener {
            val frequency = 38000 // 대부분의 장치에서 사용하는 통신 주파수
            val pattern = getSamsungTvPowerCode()
            irManager.transmit(frequency, pattern)

            Toast.makeText(requireContext(), "on and off", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSamsungTvPowerCode(): IntArray {
        val customCode = "0707".toInt(16)
        val data = "02".toInt(16)
        val complementData = "FD".toInt(16)

        val pattern = mutableListOf<Int>()
        // Leader
        pattern.add(4500) // low
        pattern.add(4500) // high

        // Custom Code
        for (i in 15 downTo 0) {
            val bit = (customCode shr i) and 1
            pattern.add(700)
            pattern.add(if (bit == 0) 400 else 1600)
        }

        // Data
        for (i in 7 downTo 0) {
            val bit = (data shr i) and 1
            pattern.add(700)
            pattern.add(if (bit == 0) 400 else 1600)
        }

        // Complement Data
        for (i in 7 downTo 0) {
            val bit = (complementData shr i) and 1
            pattern.add(700)
            pattern.add(if (bit == 0) 400 else 1600)
        }

        return pattern.toIntArray()
    }
}
