package com.ssafy.phonesin.ui.mobile.returnmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentReturnFinishBinding
import com.ssafy.phonesin.ui.util.Util
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnFinishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReturnFinishFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentReturnFinishBinding

    val returnViewModel: ReturnViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReturnFinishBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReturnFinishUi()
    }

    private fun setReturnFinishUi() = with(binding) {

        textViewReturnFinishDate.text = Util.getCurrentKoreaTime()
        textViewReturnFinishMobiles.text = "총 ${returnViewModel.returnList.size}개"

        if (returnViewModel.returnList[0].backDeliveryLocationType == "방문 택배 선택") {
            mapViewReturnFinish.isVisible = false
        } else {
            val data = arguments
            if (data != null) {
                val longitude = data.getDouble("longitude")
                val latitude = data.getDouble("latitude")
                val name = data.getString("name")
                val marker = MapPOIItem()
                marker.itemName = name
                marker.tag = 0
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                marker.markerType = MapPOIItem.MarkerType.BluePin
                mapViewReturnFinish.addPOIItem(marker)
                val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                val zoomLevel = 5
                mapViewReturnFinish.setMapCenterPointAndZoomLevel(mapPoint, zoomLevel, true)
            }
        }

        textViewReturnFinishDetailDate.text =
            " - 반납날짜 : ${returnViewModel.returnList[0].backDeliveryDate.toString()}"
        textViewReturnFinishDetailSolve.text =
            " - 수거방법 : ${returnViewModel.returnList[0].backDeliveryLocationType}"
        textViewReturnFinishDetailAddress.text =
            " - 주소 : ${returnViewModel.returnList[0].backDeliveryLocation}"

        buttonReturnHome.setOnClickListener {
            findNavController().navigate(
                R.id.action_returnFinishFragment_to_mobile,
            )
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.removeView(binding.mapViewReturnFinish)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnFinishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnFinishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}