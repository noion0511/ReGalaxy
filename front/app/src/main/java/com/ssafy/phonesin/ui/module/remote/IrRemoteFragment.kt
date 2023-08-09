package com.ssafy.phonesin.ui.module.remote

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentIrRemoteBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment

class IrRemoteFragment : BaseFragment<FragmentIrRemoteBinding>(R.layout.fragment_ir_remote) {

    private lateinit var irManager: ConsumerIrManager

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentIrRemoteBinding {
        return FragmentIrRemoteBinding.inflate(inflater, container, false)
    }

    override fun init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            showToast("Your device does not support IR transmission.")
            return
        }

        irManager = requireContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        if (!irManager.hasIrEmitter()) {
            showToast("No IR emitter found on this device.")
            return
        }

        // 여기에 버튼 이벤트 리스너 설정
        bindingNonNull.powerOnButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvPowerOnCode())
            showToast("on")
        }

        bindingNonNull.powerOffButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvPowerOffCode())
            showToast("off")
        }

        bindingNonNull.volumeUpButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeUpCode())
        }

        bindingNonNull.volumeDownButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeDownCode())
        }

        bindingNonNull.channelUpButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelUpCode())
        }

        bindingNonNull.channelDownButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelDownCode())
        }
    }

    private fun getSamsungTvPowerOnCode() = createIrPattern(0xE0E09966)
    private fun getSamsungTvPowerOffCode() = createIrPattern(0xE0E040BF)
    private fun getSamsungTvVolumeUpCode() = createIrPattern(0xE0E0E01F)
    private fun getSamsungTvVolumeDownCode() = createIrPattern(0xE0E0D02F)
    private fun getSamsungTvChannelUpCode() = createIrPattern(0xE0E048B7)
    private fun getSamsungTvChannelDownCode() = createIrPattern(0xE0E08877)


    private fun createIrPattern(hexCode: Long): IntArray {
        val pattern = mutableListOf<Int>()

        // Header
        pattern.add(SAMSUNG_HDR_MARK)
        pattern.add(SAMSUNG_HDR_SPACE)

        // Data
        for (i in 31 downTo 0) {
            pattern.add(SAMSUNG_BIT_MARK)
            pattern.add(if ((hexCode shr i and 1L) == 1L) SAMSUNG_ONE_SPACE else SAMSUNG_ZERO_SPACE)
        }

        // Footer
        pattern.add(SAMSUNG_BIT_MARK)
        pattern.add(SAMSUNG_MIN_GAP)

        return pattern.toIntArray()
    }

    companion object {
        private const val SAMSUNG_TICK = 560
        private const val SAMSUNG_HDR_MARK = SAMSUNG_TICK * 8
        private const val SAMSUNG_HDR_SPACE = SAMSUNG_TICK * 8
        private const val SAMSUNG_BIT_MARK = SAMSUNG_TICK
        private const val SAMSUNG_ONE_SPACE = SAMSUNG_TICK * 3
        private const val SAMSUNG_ZERO_SPACE = SAMSUNG_TICK
        private const val SAMSUNG_MIN_GAP = SAMSUNG_TICK * 100 // Choose an appropriate value here
    }
}
