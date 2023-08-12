package com.ssafy.phonesin.ui.module.remote

import android.content.Context
import android.hardware.ConsumerIrManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentIrRemoteBinding
import com.ssafy.phonesin.model.DeviceType
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IrRemoteFragment : BaseFragment<FragmentIrRemoteBinding>(R.layout.fragment_ir_remote) {

    private lateinit var irManager: ConsumerIrManager
    private var selectedDevice: DeviceType = DeviceType.TV

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIrRemoteBinding {
        return FragmentIrRemoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        initSelectDeviceType()

        irManager =
            requireContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        if (!irManager.hasIrEmitter()) {
            showNoIREmitterDialog()
            return
        }

        initPowerButton()
        initVolumeButton()
        initChannelButton()
        initNumberButton()
    }

    internal fun showNoIREmitterDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("IR Emitter Not Found")
        alertDialogBuilder.setMessage("해당 기기에는 적외선 발신기가 없어 리모컨 기능을 사용할 수 없습니다.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            requireActivity().onBackPressed()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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
        return createIrPattern(NUMBER_CODES[number])
    }

    // 음소거 패턴 생성 메서드
    private fun getSamsungTvMuteCode() = createIrPattern(0xE0E0F00F)


    internal fun createIrPattern(hexCode: Long): IntArray {
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
            bindingNonNull.layoutChoiceAirConditioner.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.button_normal_big)
            bindingNonNull.layoutChoiceTv.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_big)
        }

        bindingNonNull.layoutChoiceAirConditioner.setOnClickListener {
            selectedDevice = DeviceType.AIR_CONDITIONER
            bindingNonNull.layoutChoiceTv.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.button_normal_big)
            bindingNonNull.layoutChoiceAirConditioner.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_big)
        }
    }

    private fun initPowerButton() {
        bindingNonNull.powerOnButton.setOnClickListener {
            irManager.transmit(38000, getPowerOnCode(selectedDevice))
        }

        bindingNonNull.powerOffButton.setOnClickListener {
            irManager.transmit(38000, getPowerOffCode(selectedDevice))
        }
    }

    private fun initVolumeButton() {
        bindingNonNull.volumeUpButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeUpCode())
        }

        bindingNonNull.volumeDownButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvVolumeDownCode())
        }

        bindingNonNull.muteButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvMuteCode())
        }
    }

    private fun initChannelButton() {
        bindingNonNull.channelUpButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelUpCode())
        }

        bindingNonNull.channelDownButton.setOnClickListener {
            irManager.transmit(38000, getSamsungTvChannelDownCode())
        }
    }

    private fun initNumberButton() {
        bindingNonNull.button0.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(0))
        }

        bindingNonNull.button1.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(1))
        }

        bindingNonNull.button2.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(2))
        }

        bindingNonNull.button3.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(3))
        }

        bindingNonNull.button4.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(4))
        }

        bindingNonNull.button5.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(5))
        }

        bindingNonNull.button6.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(6))
        }

        bindingNonNull.button7.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(7))
        }

        bindingNonNull.button8.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(8))
        }

        bindingNonNull.button9.setOnClickListener {
            irManager.transmit(38000, getSamsungTvNumberCode(9))
        }
    }

    companion object {
        private const val SAMSUNG_TICK = 560
        internal const val SAMSUNG_HDR_MARK = SAMSUNG_TICK * 8
        internal const val SAMSUNG_HDR_SPACE = SAMSUNG_TICK * 8
        internal const val SAMSUNG_BIT_MARK = SAMSUNG_TICK
        internal const val SAMSUNG_ONE_SPACE = SAMSUNG_TICK * 3
        internal const val SAMSUNG_ZERO_SPACE = SAMSUNG_TICK
        internal const val SAMSUNG_MIN_GAP = SAMSUNG_TICK * 100

        private val NUMBER_CODES = arrayOf(
            0xE0E020DF, 0xE0E030CF, 0xE0E010EF,
            0xE0E0906F, 0xE0E050AF, 0xE0E0609F,
            0xE0E0708F, 0xE0E040BF, 0xE0E0807F,
            0xE0E000FF
        )
    }
}
