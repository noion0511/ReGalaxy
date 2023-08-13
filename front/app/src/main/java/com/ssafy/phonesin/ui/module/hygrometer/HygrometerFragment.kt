package com.ssafy.phonesin.ui.module.hygrometer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ssafy.phonesin.ApplicationClass
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentHygrometerBinding
import com.ssafy.phonesin.model.Location
import com.ssafy.phonesin.ui.util.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.SimpleTimeZone

class HygrometerFragment : BaseFragment<FragmentHygrometerBinding>(R.layout.fragment_hygrometer),
    SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var humiditySensor: Sensor? = null
    private var temperatureSensor: Sensor? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var current: android.location.Location

    val hygrometerViewModel : HygrometerViewModel by activityViewModels()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHygrometerBinding {
        return FragmentHygrometerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        getWeatherInfo()

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if (humiditySensor == null) {
            showToast("습도 센서가 내장되어 있지 않아 관련 서비스가 제공되지 않습니다.")
        }

        if (temperatureSensor == null) {
            showToast("온도 센서가 내장되어 있지 않아 관련 서비스가 제공되지 않습니다.")
        }


        hygrometerViewModel.nowClimate.observe(viewLifecycleOwner, Observer {
            bindingNonNull.textViewTemperate.text = hygrometerViewModel.nowClimate.value?.climate!!.toInt().toString() ?: "알수없음"
            bindingNonNull.textViewLocation.text = hygrometerViewModel.nowClimate.value?.address ?: "알수없음"
        })


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

    private fun getWeatherInfo() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                ApplicationClass.PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        current = location
                        hygrometerViewModel.getClimate(Location(current.latitude,current.longitude))
                        val nowTime = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                        bindingNonNull.textViewDate.text = nowTime
                    }
                }
                .addOnFailureListener { exception ->
                    showToast("위치정보를 받아오지 못하였습니다.")
                }
        }


    }
}
