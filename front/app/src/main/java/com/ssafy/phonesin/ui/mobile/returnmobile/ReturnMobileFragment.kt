package com.ssafy.phonesin.ui.mobile.returnmobile

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentReturnMobileBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.Util.convertCalendarToDate
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnMobileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ReturnMobileFragment : BaseFragment<FragmentReturnMobileBinding>(
    R.layout.fragment_return_mobile
) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val returnMobileViewModel: ReturnMobileViewModel by viewModels()
    val returnViewModel: ReturnViewModel by activityViewModels()
    val rentalCheckList = mutableListOf<CheckBox>()


    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReturnMobileBinding {
        return FragmentReturnMobileBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setReturnMobile()
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


    private fun setReturnMobile() = with(bindingNonNull) {

        returnMobileViewModel.rentalResponseList.observe(viewLifecycleOwner) {
            layoutReturnCheckBox.removeAllViews()
            it.forEach { rental ->
                val checkBox = CheckBox(context)
                checkBox.text = "${rental.modelName} No.${rental.phoneId}"
//                radioButton.id = it.indexOf(rental)
                checkBox.id = rental.phoneId.toString().toInt()
                checkBox.tag = rental.rentalId
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    checkBox.setTextColor(Color.BLACK)
                }
                layoutReturnCheckBox.addView(checkBox)
                rentalCheckList.add(checkBox)
            }
        }

//        for (i in 1..3) {
//            val radioButton = RadioButton(context)
//            radioButton.text = "RadioButton $i"
//            radioButton.id = i // 라디오 버튼마다 고유한 ID를 부여 (무조건 필요한 것은 아님)
//            radioGroupReturnAdd.addView(radioButton)
//        }

        buttonReturnNext.setOnClickListener {
            if (!isCheckBox()) {//하나라도 체크 안돼있음
                showToast("한개 이상을 체크해주세요")
            } else if (editTextReturnContent.text.toString() == "") {
                showToast("후기를 적어주세요")
            } else {
                returnViewModel.setReturnList(getPhoneIdCheckBox(),getRentalIdCheckBox())
                returnViewModel.setReturnListDate(convertCalendarToDate(calendarReturn.date))
                returnViewModel.setReturnListContent(editTextReturnContent.text.toString())

                if (radioButtonAgent.isChecked) {
                    returnViewModel.setReturnListType(radioButtonAgent.text.toString())
                    findNavController().navigate(
                        R.id.action_returnMobileFragment_to_returnAgentFragment,
                    )
                } else {
                    returnViewModel.setReturnListType(radioButtonVisitDelivery.text.toString())
                    findNavController().navigate(
                        R.id.action_returnMobileFragment_to_returnVisitDeliveryFragment,
                    )
                }
            }
        }

    }

    private fun isCheckBox(): Boolean {
        return rentalCheckList.any { it.isChecked }
    }

    private fun getPhoneIdCheckBox(): List<Int> {
        return bindingNonNull.layoutReturnCheckBox.children
            .filterIsInstance<CheckBox>()
            .filter { it.isChecked }
            .map { it.id }.toList()
    }

    private fun getRentalIdCheckBox(): List<Int> {
        return bindingNonNull.layoutReturnCheckBox.children
            .filterIsInstance<CheckBox>()
            .filter { it.isChecked }
            .map { it.tag.toString().toInt() }.toList()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnMobileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnMobileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}