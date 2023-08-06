package com.ssafy.phonesin.ui.mobile.donatemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentDonateAgentDetailBinding
import com.ssafy.phonesin.ui.util.base.BaseFragment
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateAgentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonateAgentDetailFragment :
    BaseFragment<FragmentDonateAgentDetailBinding>(R.layout.fragment_donate_agent_detail) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val donateViewModel: DonateViewModel by activityViewModels()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateAgentDetailBinding {
        return FragmentDonateAgentDetailBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setDonateAgentDetailUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private fun setDonateAgentDetailUi() = with(bindingNonNull) {

        val data = arguments
        if (data != null) {
            val address = data.getString("address")
            val name = data.getString("name")
            val longitude = data.getDouble("longitude")
            val latitude = data.getDouble("latitude")

            donateViewModel.setLocationDonate(address.toString())//여기 있어도 되나?

            textViewDonateAgentDetailTitle.text = name
            textViewDonateAgentDetailAddress.text = address

            val marker = MapPOIItem()
            marker.itemName = name
            marker.tag = 0
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            marker.markerType = MapPOIItem.MarkerType.BluePin
            mapViewDonateAgentDetail.addPOIItem(marker)
            val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            val zoomLevel = 5
            mapViewDonateAgentDetail.setMapCenterPointAndZoomLevel(mapPoint, zoomLevel, true)
        }




        buttonPostDonateAgent.setOnClickListener {
            findNavController().navigate(
                R.id.action_donateAgentDetailFragment_to_doateFinishFragment,
            )
            mapViewDonateAgentDetail.visibility = View.GONE
            donateViewModel.uploadDonation()
            //binding.root.removeView(binding.mapViewDonateAgentDetail)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DonateAgentDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonateAgentDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}