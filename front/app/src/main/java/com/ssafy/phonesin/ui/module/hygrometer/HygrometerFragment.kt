package com.ssafy.phonesin.ui.module.hygrometer

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ssafy.phonesin.ApplicationClass
import com.ssafy.phonesin.R
import com.ssafy.phonesin.common.AppPreferences
import com.ssafy.phonesin.databinding.FragmentHygrometerBinding
import com.ssafy.phonesin.model.Hygrometer
import com.ssafy.phonesin.model.Location
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import java.text.SimpleDateFormat


class HygrometerFragment : BaseFragment<FragmentHygrometerBinding>(R.layout.fragment_hygrometer),
    SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var humiditySensor: Sensor? = null
    private var temperatureSensor: Sensor? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var current: android.location.Location

    private lateinit var hygrometerList: MutableList<Hygrometer>
    private lateinit var lineChart: LineChart

    val hygrometerViewModel: HygrometerViewModel by activityViewModels()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHygrometerBinding {
        return FragmentHygrometerBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
        mainActivity.setHygrometerPadding(bindingNonNull.hygrometerLayout)
        getWeatherInfo()
        setLastHygrometer()

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if (humiditySensor == null) {
            showToast("습도 센서가 내장되어 있지 않아 관련 서비스가 제공되지 않습니다.")
        }

        if (temperatureSensor == null) {
            showToast("온도 센서가 내장되어 있지 않아 관련 서비스가 제공되지 않습니다.")
        }

        bindingNonNull.buttonChart.setOnClickListener {
            setChartDialog()
        }

        hygrometerViewModel.nowClimate.observe(viewLifecycleOwner, Observer {
            val climate = hygrometerViewModel.nowClimate.value
            if (climate != null) {
                bindingNonNull.textViewLocationTemperate.text = climate.climate.toInt().toString()
                bindingNonNull.textViewLocationHumidity.text = climate.humidity.toString()
                bindingNonNull.textViewLocation.text = climate.address

                if (climate.weather == "Clear") {
                    bindingNonNull.imageviewWeather.setImageResource(R.drawable.clear)
                    bindingNonNull.hygrometerLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.hygrometerBackgroundClear
                        )
                    )
                } else if (climate.weather == "Clouds") {
                    bindingNonNull.imageviewWeather.setImageResource(R.drawable.clouds)
                    bindingNonNull.hygrometerLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.hygrometerBackgroundClouds
                        )
                    )
                } else if (climate.weather == "Rain") {
                    bindingNonNull.imageviewWeather.setImageResource(R.drawable.rain)
                    bindingNonNull.hygrometerLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.hygrometerBackgroundRain
                        )
                    )
                }
            }
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

    override fun onDetach() {
        super.onDetach()
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(false)
        mainActivity.setPadding()
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
                        hygrometerViewModel.getClimate(
                            Location(
                                current.latitude,
                                current.longitude
                            )
                        )
                        val nowTime =
                            SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                        bindingNonNull.textViewDate.text = nowTime
                    }
                }
                .addOnFailureListener { exception ->
                    showToast("위치정보를 받아오지 못하였습니다.")
                }
        }
    }

    private fun setLastHygrometer() {
        val today = SimpleDateFormat("MM-dd").format(System.currentTimeMillis())
        hygrometerList = if (AppPreferences.getHygrometerList() is MutableList<Hygrometer>) AppPreferences.getHygrometerList() as MutableList<Hygrometer> else mutableListOf()
//        if (!AppPreferences.checkLastHygrometerDate(today)) {
//            hygrometerList.add(Hygrometer(today, bindingNonNull.textViewTemperature.text.toString().toInt(), bindingNonNull.textViewHumidity.text.toString().toInt()))
        val temp = "08-14"
        if (!AppPreferences.checkLastHygrometerDate(temp)) {
            hygrometerList.add(Hygrometer(temp, 32, 82))
            if (hygrometerList.size > 7) {
                hygrometerList.removeAt(0)
            }
            AppPreferences.saveHygrometerList(hygrometerList)
//            AppPreferences.setLastHygrometerDate(today)
            AppPreferences.setLastHygrometerDate(temp)
        }
        Log.d("저장리스트", "setLastHygrometer: ${hygrometerList}")
    }

    private fun setChartDialog() {
        val chartDialog = Dialog(requireContext())

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            chartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        chartDialog.setContentView(R.layout.dialog_hygrometer_chart)
        chartDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius
        shape.setColor(Color.WHITE)
        chartDialog.window?.setBackgroundDrawable(shape)

        lineChart = chartDialog.findViewById<LineChart>(R.id.chart)

        setChart()

        chartDialog.show()
    }

    private fun setChart() {
        configureChartAppearance()
        lineChart.apply {
            data = createChartData()
            notifyDataSetChanged()
            invalidate()
        }
    }

    private fun configureChartAppearance() {
        lineChart.extraBottomOffset = 15f // 간격
        lineChart.description.isEnabled = false // chart 밑에 description 표시 유무

        lineChart.apply {
            axisRight.isEnabled = false   //y축 사용여부
            axisLeft.isEnabled = true
            legend.isEnabled = false    //legend 사용여부
            description.isEnabled = false //주석
            isDragXEnabled = true   // x 축 드래그 여부
            isScaleYEnabled = false //y축 줌 사용여부
            isScaleXEnabled = false //x축 줌 사용여부
        }

        lineChart.xAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 15f
            labelRotationAngle = 0f
            setLabelCount(hygrometerList.size, true)
        }

        lineChart.axisLeft.apply {
            isEnabled = true
            textSize = 15f
            setDrawAxisLine(false)
            axisLineWidth = 10f
            axisMinimum = 0f
            axisMaximum = 100f
        }
    }

    private fun createChartData(): LineData {
        val entry1 = ArrayList<Entry>() // 앱1
        val entry2 = ArrayList<Entry>() // 앱2
        val chartData = LineData()

        hygrometerList.forEachIndexed() { index, hygrometer ->
            entry1.add(Entry(index.toFloat(), hygrometer.temperate.toFloat()))
            entry2.add(Entry(index.toFloat(), hygrometer.humidity.toFloat()))
        }

        // 앱1
        val lineDataSet1 = LineDataSet(entry1, "실내 온도")
        chartData.addDataSet(lineDataSet1)
        lineDataSet1.lineWidth = 3f
        lineDataSet1.circleRadius = 6f
        lineDataSet1.setDrawValues(false)
        lineDataSet1.setDrawCircleHole(true)
        lineDataSet1.setDrawCircles(true)
        lineDataSet1.setDrawHorizontalHighlightIndicator(false)
        lineDataSet1.setDrawHighlightIndicators(false)
        lineDataSet1.color = Color.rgb(242, 79, 89)
        lineDataSet1.setCircleColor(Color.rgb(242, 79, 89))

        // 앱2
        val lineDataSet2 = LineDataSet(entry2, "실내 습도")
        chartData.addDataSet(lineDataSet2)
        lineDataSet2.lineWidth = 3f
        lineDataSet2.circleRadius = 6f
        lineDataSet2.setDrawValues(false)
        lineDataSet2.setDrawCircleHole(true)
        lineDataSet2.setDrawCircles(true)
        lineDataSet2.setDrawHorizontalHighlightIndicator(false)
        lineDataSet2.setDrawHighlightIndicators(false)
        lineDataSet2.color = Color.rgb(58, 176, 255)
        lineDataSet2.setCircleColor(Color.rgb(58, 176, 255))

        return chartData
    }
}

