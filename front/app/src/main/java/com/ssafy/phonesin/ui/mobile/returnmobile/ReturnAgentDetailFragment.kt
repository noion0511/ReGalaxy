package com.ssafy.phonesin.ui.mobile.returnmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentReturnAgentDetailBinding
import com.ssafy.phonesin.ui.util.setDebouncingClickListener
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnAgentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReturnAgentDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentReturnAgentDetailBinding
    val returnViewModel: ReturnViewModel by activityViewModels()
    val bundle = bundleOf()

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
        binding = FragmentReturnAgentDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReturnAgentDetailUi()
    }

    private fun setReturnAgentDetailUi() = with(binding) {
        //TODO 코드정리 필요...
        val data = arguments
        if (data != null) {
            val address = data.getString("address")
            val name = data.getString("name")
            val longitude = data.getDouble("longitude")
            val latitude = data.getDouble("latitude")

            bundle.putDouble("longitude", longitude)
            bundle.putDouble("latitude", latitude)
            bundle.putString("name", name)


            returnViewModel.setReturnListAddress(address.toString())//여기 있어도 되나?

            textViewReturnAgentDetailTitle.text = name
            textViewReturnAgentDetailAddress.text = address

            val marker = MapPOIItem()
            marker.itemName = name
            marker.tag = 0
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            marker.markerType = MapPOIItem.MarkerType.BluePin
            mapViewReturnAgentDetail.addPOIItem(marker)
            val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            val zoomLevel = 5
            mapViewReturnAgentDetail.setMapCenterPointAndZoomLevel(mapPoint, zoomLevel, true)
        }

        buttonPostReturnAgent.setDebouncingClickListener {
            findNavController().navigate(
                R.id.action_returnAgentDetailFragment_to_returnFinishFragment, bundle
            )
            returnViewModel.uploadReturn()
            binding.root.removeView(binding.mapViewReturnAgentDetail)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnAgentDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnAgentDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}