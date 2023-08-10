package com.ssafy.phonesin.ui.mobile.donatemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentDonateFinishBinding
import com.ssafy.phonesin.ui.util.Util.getCurrentKoreaTime
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateFinishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonateFinishFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentDonateFinishBinding

    val donateViewModel: DonateViewModel by activityViewModels()

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
        binding = FragmentDonateFinishBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDonateFinishUi()
    }

    private fun setDonateFinishUi() = with(binding) {

        textViewDonateFinishDate.text = getCurrentKoreaTime()

        if (donateViewModel.donation.donationDeliveryLocationType == "방문 택배 선택") {
            mapViewDonateFinish.isVisible = false
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
                mapViewDonateFinish.addPOIItem(marker)
                val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                val zoomLevel = 5
                mapViewDonateFinish.setMapCenterPointAndZoomLevel(mapPoint, zoomLevel, true)
            }
        }

        textViewDonateFinishDetailDate.text =
            " - 기증날짜 : ${donateViewModel.donation.donationDeliveryDate.toString()}"
        textViewDonateFinishDetailSolve.text =
            " - 수거방법 : ${donateViewModel.donation.donationDeliveryLocationType}"
        textViewDonateFinishDetailAddress.text =
            " - 주소 : ${donateViewModel.donation.donationDeliveryLocation}"


        buttonDonateHome.setOnClickListener {
            findNavController().navigate(
                R.id.action_doateFinishFragment_to_mobile,
            )
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.removeView(binding.mapViewDonateFinish)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DoateFinishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonateFinishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}