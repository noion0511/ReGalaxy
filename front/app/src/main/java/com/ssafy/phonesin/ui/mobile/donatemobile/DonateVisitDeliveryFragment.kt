package com.ssafy.phonesin.ui.mobile.donatemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentDonateVisitDeliveryBinding
import com.ssafy.phonesin.ui.mobile.MobileViewModel
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DonateVisitDeliveryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DonateVisitDeliveryFragment :
    BaseFragment<FragmentDonateVisitDeliveryBinding>(R.layout.fragment_donate_visit_delivery) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val donateVisitDeliveryViewModel: DonateViewModel by activityViewModels()
    val mobileViewModel: MobileViewModel by activityViewModels()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDonateVisitDeliveryBinding {
        return FragmentDonateVisitDeliveryBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setDonateVisitDeliveryUi()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun setDonateVisitDeliveryUi() = with(bindingNonNull) {

        radioGroupDonateDelivery.setOnCheckedChangeListener { _, checkedId ->
            // 라디오 버튼 상태에 따라 EditText 클릭 가능 여부 설정
            when (checkedId) {
                R.id.radioButtonDonateVisitDeliveryExistAddress -> {
                    spinnerDonateAddress.isEnabled = true
                    editTextDonateAddress.isEnabled = false
                }

                R.id.radioButtonDonateVisitDeliveryNewAddress -> {
                    spinnerDonateAddress.isEnabled = false
                    editTextDonateAddress.isEnabled = true
                }
            }
        }

        if (mobileViewModel.addressList.size == 0) {
            radioButtonDonateVisitDeliveryNewAddress.isChecked = true
            radioButtonDonateVisitDeliveryExistAddress.isChecked = false
            radioButtonDonateVisitDeliveryExistAddress.isClickable = false
            spinnerDonateAddress.isEnabled = false
        } else {
            radioButtonDonateVisitDeliveryNewAddress.isChecked = false
            radioButtonDonateVisitDeliveryExistAddress.isChecked = true
            spinnerDonateAddress.isEnabled = true
            editTextDonateAddress.isEnabled = false

            spinnerDonateAddress.setItems(mobileViewModel.addressList.map { it.address }
                .toList())
            spinnerDonateAddress.setIsFocusable(true)
            spinnerDonateAddress.selectItemByIndex(0)
        }

        buttonPostDonateVisitDelivery.setOnClickListener {

            if (editTextDonateAddress.text.toString() == "" && radioButtonDonateVisitDeliveryNewAddress.isChecked) {
                showToast("주소를 입력하세요!")
            } else {
                donateVisitDeliveryViewModel.donation.donationDeliveryLocationType =
                    if (radioButtonDonateVisitDeliveryExistAddress.isChecked) {
                        spinnerDonateAddress.text.toString()
                    } else {
                        editTextDonateAddress.text.toString()
                    }
                //donateVisitDeliveryViewModel.uploadDonation()
                findNavController().navigate(
                    R.id.action_donateVisitDeliveryFragment_to_doateFinishFragment,
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
         * @return A new instance of fragment DonateVisitDeliveryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonateVisitDeliveryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}