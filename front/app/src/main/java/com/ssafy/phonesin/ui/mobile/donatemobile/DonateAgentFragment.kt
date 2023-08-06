package com.ssafy.phonesin.ui.mobile.donatemobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ssafy.phonesin.ApplicationClass.Companion.PERMISSIONS_REQUEST_LOCATION
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentDonateAgentBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateAgentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonateAgentFragment :
    BaseFragment<FragmentDonateAgentBinding>(R.layout.fragment_donate_agent) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateAgentBinding {
        return FragmentDonateAgentBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setDonateAgentUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // 위치 권한 요청
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없을 경우 권한 요청
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            // 권한이 있을 경우 위치 업데이트 요청
            requestLocationUpdates()
        }


    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // 위치 정보를 가져왔을 때 처리하는 로직
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.e("싸피", latitude.toString() + longitude.toString())

                    // TODO: 위도(latitude)와 경도(longitude)를 이용해 원하는 작업 수행
                    // 예를 들어, 지도에 현재 위치 표시 등
                }
            }
            .addOnFailureListener { exception ->
                // 위치 정보 가져오기 실패 처리
                // 예외 처리 등
            }
    }


    private fun setDonateAgentUi() = with(bindingNonNull) {
        // TODO: 리사이클러뷰에 클릭이벤트로 바꿔야함
//        buttonTemp.setOnClickListener {
//            findNavController().navigate(
//                R.id.action_donateAgentFragment_to_donateAgentDetailFragment,
//            )
//        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DonateAgentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonateAgentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}