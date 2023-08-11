package com.ssafy.phonesin.ui.mobile.donatemobile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentDonateMobileBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.Util.convertCalendarToDate
import com.ssafy.phonesin.ui.util.Util.convertCalendarToDateHyphen
import com.ssafy.phonesin.ui.util.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateMobileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DonateMobileFragment :
    BaseFragment<FragmentDonateMobileBinding>(R.layout.fragment_donate_mobile) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val donateMobileViewModel: DonateViewModel by activityViewModels()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateMobileBinding {
        return FragmentDonateMobileBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setDonateMobileUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }


    private fun setDonateMobileUi() = with(bindingNonNull) {
        val apiVersion = Build.VERSION.SDK_INT

        if (apiVersion <= 18) {
            val temp = calendarDonate.layoutParams
            temp.height = 500
            calendarDonate.layoutParams = temp

        }

        buttonDonateNext.setOnClickListener {

            donateMobileViewModel.setDateDonate(convertCalendarToDate(calendarDonate.date))
//            donateMobileViewModel.setDateDonate(convertCalendarToDate(calendarDonate.date))
            if (radioButtonDonateVisitDelivery.isChecked) {
                donateMobileViewModel.setTypeDonate(radioButtonDonateVisitDelivery.text.toString())

                findNavController().navigate(
                    R.id.action_donateMobileFragment_to_donateVisitDeliveryFragment,
                )
            } else {
                donateMobileViewModel.setTypeDonate(radioButtonDonateAgent.text.toString())
                findNavController().navigate(
                    R.id.action_donateMobileFragment_to_donateAgentFragment,
                )
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DonateMobileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonateMobileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}