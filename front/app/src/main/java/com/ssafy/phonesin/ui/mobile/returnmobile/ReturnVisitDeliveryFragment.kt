package com.ssafy.phonesin.ui.mobile.returnmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentReturnVisitDeliveryBinding
import com.ssafy.phonesin.ui.mobile.MobileViewModel
import com.ssafy.phonesin.ui.util.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnVisitDeliveryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReturnVisitDeliveryFragment :
    BaseFragment<FragmentReturnVisitDeliveryBinding>(R.layout.fragment_return_visit_delivery) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val mobileViewModel: MobileViewModel by activityViewModels()
    val returnViewModel: ReturnViewModel by activityViewModels()
    lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReturnVisitDeliveryBinding {
        return FragmentReturnVisitDeliveryBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setReturnVisitDeliveryUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun setReturnVisitDeliveryUi() = with(bindingNonNull) {

        radioGroupReturnDelivery.setOnCheckedChangeListener { _, checkedId ->
            // 라디오 버튼 상태에 따라 EditText 클릭 가능 여부 설정
            when (checkedId) {
                R.id.radioButtonVisitDeliveryExistAddress -> {
                    spinnerReturnAddress.isEnabled = true
                    editTextReturnAddress.isEnabled = false
                }

                R.id.radioButtonVisitDeliveryNewAddress -> {
                    spinnerReturnAddress.isEnabled = false
                    editTextReturnAddress.isEnabled = true
                }
            }
        }

        setAdapter(R.layout.custom_text_style_black)

        if (mobileViewModel.addressList.size == 1 && mobileViewModel.addressList[0].addressId == -1) {
            spinnerAdapter = setAdapter(R.layout.custom_text_style_gray)
            radioButtonVisitDeliveryNewAddress.isChecked = true
            radioButtonVisitDeliveryExistAddress.isChecked = false
            radioButtonVisitDeliveryExistAddress.isClickable = false
            spinnerReturnAddress.isEnabled = false
        } else {
            spinnerAdapter = setAdapter(R.layout.custom_text_style_black)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            radioButtonVisitDeliveryNewAddress.isChecked = false
            radioButtonVisitDeliveryExistAddress.isChecked = true
            spinnerReturnAddress.isEnabled = true
            editTextReturnAddress.isEnabled = false

        }
        spinnerReturnAddress.adapter = spinnerAdapter

        buttonPostReturnVisitDelivery.setOnClickListener {
            if (editTextReturnAddress.text.toString() == "" && radioButtonVisitDeliveryNewAddress.isChecked) {
                showToast("주소를 입력하세요!")
            } else {
                returnViewModel.setReturnListAddress(
                    if (radioButtonVisitDeliveryExistAddress.isChecked) {
                        spinnerReturnAddress.selectedItem.toString()
                    } else {
                        editTextReturnAddress.text.toString()
                    }
                )
                returnViewModel.uploadReturn()
                findNavController().navigate(
                    R.id.action_returnVisitDeliveryFragment_to_returnFinishFragment,
                )
            }
        }
    }

    private fun setAdapter(id: Int): ArrayAdapter<String> {
        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            id,
            mobileViewModel.addressList.map { it.address }
                .toList())
        return spinnerAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnVisitDeliveryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnVisitDeliveryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}