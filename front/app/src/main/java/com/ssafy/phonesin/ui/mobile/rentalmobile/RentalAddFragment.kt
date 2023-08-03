package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentRentalAddBinding
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentalAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RentalAddFragment :
    BaseFragment<FragmentRentalAddBinding>(R.layout.fragment_rental_add) {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val parentViewModel: RentalViewModel by activityViewModels()

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
    ): FragmentRentalAddBinding {
        return FragmentRentalAddBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setRentalAddUi()
    }

    private fun setRentalAddUi() = with(bindingNonNull) {
        radioButtonRentalAddOneMonth.isChecked = true
        spinnerRentalAddAddress.setItems(parentViewModel.addressList.map { it.address }.toList())

        if (parentViewModel.addressList.size == 0) {
            radioButtonNewAddress.isChecked = true
            radioButtonExistAddress.isClickable = false
        }

        buttonSelectMobile.setOnClickListener {
            if (!checkBoxHomeCam.isChecked && !checkBoxTemperature.isChecked && !checkBoxPicture.isChecked) {
                showToast("기능을 선택하세요!")
            } else if (editTextRentalAddAddress.text.toString() == "" && radioButtonNewAddress.isChecked) {
                showToast("주소를 입력하세요!")
            } else {
                //TODO : memberId삭제
                val address = if (radioButtonExistAddress.isChecked) {
                    spinnerRentalAddAddress.text.toString()
                } else {
                    editTextRentalAddAddress.text.toString()
                }
                val month = checkDate()


                val data = Rental(
                    checkBoxTemperature.isChecked,
                    1,
                    -1,
                    20000,
                    checkBoxHomeCam.isChecked,
                    8,
                    -1,
                    address,
                    month,
                    checkBoxPicture.isChecked
                )
                parentViewModel.addRental(data)

                findNavController().navigate(
                    R.id.action_rentalAddFragment_to_rentalMobileFragment2,
                )
            }


        }

        if (radioButtonExistAddress.isChecked) {
            editTextRentalAddAddress.isClickable = false
            spinnerRentalAddAddress.isClickable = true
        }
        if (radioButtonNewAddress.isChecked) {
            editTextRentalAddAddress.isClickable = true
            spinnerRentalAddAddress.isClickable = false
        }

    }

    private fun checkDate(): Int {
        return if (bindingNonNull.radioButtonRentalAddOneMonth.isChecked) {
            1
        } else if (bindingNonNull.radioButtonRentalAddTwoMonth.isChecked) {
            2
        } else if (bindingNonNull.radioButtonRentalAddThreeMonth.isChecked) {
            3
        } else if (bindingNonNull.radioButtonRentalAddFourMonth.isChecked) {
            4
        } else if (bindingNonNull.radioButtonRentalAddFiveMonth.isChecked) {
            5
        } else {
            6
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RentalAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentalAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}