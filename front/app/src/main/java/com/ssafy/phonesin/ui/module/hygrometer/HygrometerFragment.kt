package com.ssafy.phonesin.ui.module.hygrometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHygrometerBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment

class HygrometerFragment : BaseFragment<FragmentHygrometerBinding>(R.layout.fragment_hygrometer),
    SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var humiditySensor: Sensor? = null
    private var temperatureSensor: Sensor? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHygrometerBinding {
        return FragmentHygrometerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if (humiditySensor == null) {
            showToast("Humidity sensor not available!")
        }

        if (temperatureSensor == null) {
            showToast("Temperature sensor not available!")
        }
    }

    override fun onResume() {
        super.onResume()
        humiditySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        temperatureSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    val humidity = it.values[0]
                    bindingNonNull.textViewHumidity.text = "Humidity: $humidity%"
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    val temperature = it.values[0]
                    bindingNonNull.textViewTemperature.text = "Temperature: $temperature°C"
                }
            }
        }
    }
}
