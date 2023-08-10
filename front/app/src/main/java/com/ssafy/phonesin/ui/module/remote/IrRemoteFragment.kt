package com.ssafy.phonesin.ui.module.remote

import android.content.Context
import android.hardware.ConsumerIrManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentIrRemoteBinding
import com.ssafy.phonesin.model.DeviceType
import com.ssafy.phonesin.ui.util.base.BaseFragment

class IrRemoteFragment : BaseFragment<FragmentIrRemoteBinding>(R.layout.fragment_ir_remote) {

    private lateinit var irManager: ConsumerIrManager
    private var selectedDevice: DeviceType = DeviceType.TV

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIrRemoteBinding {
        return FragmentIrRemoteBinding.inflate(inflater, container, false)
    }

    override fun init() {
        initSelectDeviceType()

        irManager =
            requireContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        if (!irManager.hasIrEmitter()) {
            showToast("No IR emitter found on this device.")
            return
        }

        // 여기에 버튼 이벤트 리스너 설정
        initPowerButton()
        initVolumeButton()
        initChannelButton()
        initNumberButton()
    }

    private fun getPowerOnCode(deviceType: DeviceType): IntArray {
        return when (deviceType) {
            DeviceType.TV -> createIrPattern(0xE0E09966)
            DeviceType.AIR_CONDITIONER -> createIrPattern(0xAABBCCDD)
        }
    }

    private fun getPowerOffCode(deviceType: DeviceType): IntArray {
        return when (deviceType) {
            DeviceType.TV -> createIrPattern(0xE0E040BF)
            DeviceType.AIR_CONDITIONER -> createIrPattern(0xAABB8877)
        }
    }

    private fun getSamsungTvVolumeUpCode() = createIrPattern(0xE0E0E01F)
    private fun getSamsungTvVolumeDownCode() = createIrPattern(0xE0E0D02F)
    private fun getSamsungTvChannelUpCode() = createIrPattern(0xE0E048B7)
    private fun getSamsungTvChannelDownCode() = createIrPattern(0xE0E08877)

    // 숫자 패턴 생성 메서드
    private fun getSamsungTvNumberCode(number: Int): IntArray {
        val hexCodes = arrayOf(
            0xE0E020DF, 0xE0E030CF, 0xE0E010EF,
            0xE0E0906F, 0xE0E050AF, 0xE0E0609F,
            0xE0E0708F, 0xE0E040BF, 0xE0E0807F,
            0xE0E000FF // 0-9까지의 HEX 코드
        )
        return createIrPattern(hexCodes[number])
    }

    // 음소거 패턴 생성 메서드
    private fun getSamsungTvMuteCode() = createIrPattern(0xE0E0F00F)


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


    private fun initSelectDeviceType() {
        bindingNonNull.layoutChoiceTv.setOnClickListener {
            selectedDevice = DeviceType.TV
            bindingNonNull.layoutChoiceAirConditioner.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_normal_big)
            bindingNonNull.layoutChoiceTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_big)
        }

        bindingNonNull.layoutChoiceAirConditioner.setOnClickListener {
            selectedDevice = DeviceType.AIR_CONDITIONER
            bindingNonNull.layoutChoiceTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_normal_big)
            bindingNonNull.layoutChoiceAirConditioner.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_big)
        }
    }

    private fun initPowerButton() {
        bindingNonNull.powerOnButton.setOnClickListener {
            irManager.transmit(38000, getPowerOnCode(selectedDevice))
        }

        bindingNonNull.powerOff.setOnClickListener {
            irManager.transmit(38000, getPowerOffCode(selectedDevice))
        }
    }

    private fun initVolumeButton() {
        bindingNonNull.volumeUp.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeUpCode())
        }

        bindingNonNull.volumeDown.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeDownCode())
        }

        bindingNonNull.mute.setOnClickListener {
            irManager.transmit(38000, getSamsungTvMuteCode())
        }
    }

    private fun initChannelButton() {
        bindingNonNull.channelUp.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelUpCode())
        }

        bindingNonNull.channelDown.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelDownCode())
        }
    }

    private fun initNumberButton() {
        bindingNonNull.number0.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(0))
        }

        bindingNonNull.number1.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(1))
        }

        bindingNonNull.number2.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(2))
        }

        bindingNonNull.number3.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(3))
        }

        bindingNonNull.number4.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(4))
        }

        bindingNonNull.number5.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(5))
        }

        bindingNonNull.number6.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(6))
        }

        bindingNonNull.number7.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(7))
        }

        bindingNonNull.number8.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(8))
        }

        bindingNonNull.number9.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(9))
        }
    }

    companion object {
        private const val SAMSUNG_TICK = 560
        private const val SAMSUNG_HDR_MARK = SAMSUNG_TICK * 8
        private const val SAMSUNG_HDR_SPACE = SAMSUNG_TICK * 8
        private const val SAMSUNG_BIT_MARK = SAMSUNG_TICK
        private const val SAMSUNG_ONE_SPACE = SAMSUNG_TICK * 3
        private const val SAMSUNG_ZERO_SPACE = SAMSUNG_TICK
        private const val SAMSUNG_MIN_GAP = SAMSUNG_TICK * 100
    }
}
