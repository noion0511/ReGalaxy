package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentRentalFinishBinding
import com.ssafy.phonesin.ui.util.Util.getCurrentKoreaTime
import com.ssafy.phonesin.ui.util.Util.selectModule
import com.ssafy.phonesin.ui.util.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentalFinishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalFinishFragment :
    BaseFragment<FragmentRentalFinishBinding>(R.layout.fragment_rental_finish) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val rentalFinishViewModel: RentalViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRentalFinishBinding {
        return FragmentRentalFinishBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setRentalFinishUi()
    }


    private fun setRentalFinishUi() = with(bindingNonNull) {

        textViewMobileInfo.text = "총 갯수 ${rentalFinishViewModel.currentRentalListSize()}개"

        textViewPostDate.text = "신청 일시 : ${getCurrentKoreaTime()}"

        textViewDetailModule.text = getText()

        buttonMobileHome.setOnClickListener {
            findNavController().navigate(
                R.id.action_rentalFinishFragment_to_mobile,
            )
            rentalFinishViewModel.clearRentalList()
        }
    }

    fun getText(): String {
        var temp = ""
        rentalFinishViewModel.rentalList.value?.forEach {
            temp += " - 신청기능 : ${selectModule(it)}\n"
            temp += " - 주소 : ${it.rentalDeliveryLocation}\n"
            temp += " - 기간 : ${it.usingDate}개월\n"
            temp += " - 갯수 : ${it.count}\n\n"
        }
        temp = temp.removeRange(temp.length - 2, temp.length)
        return temp
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RentalFinishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentalFinishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}