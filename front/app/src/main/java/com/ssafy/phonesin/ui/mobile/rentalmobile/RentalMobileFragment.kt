package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentRentalMoblieBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentalMobileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class RentalMobileFragment :
    BaseFragment<FragmentRentalMoblieBinding>(R.layout.fragment_rental_moblie) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val rentalMobileViewModel: RentalViewModel by activityViewModels()
    var rentalCount = 0

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRentalMoblieBinding {
        return FragmentRentalMoblieBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

    }

    override fun init() {
        rentalMobileUi()
        setRentalMobileAdapter()
    }

    private fun setRentalMobileAdapter() {
        val rentalMobileAdapter = RentalMobileAdapter()
        rentalMobileAdapter.deleteRentalMobileListener =
            object : RentalMobileAdapter.DeleteRentalMobileListener {
                override fun onClick(id: Int) {
                    rentalMobileViewModel.deleteRental(id)
                }
            }
        rentalMobileAdapter.plusRentalMobileListener =
            object : RentalMobileAdapter.PlusRentalMobileListener {
                override fun onClick(id: Int) {
                    val count = currentRentalList() ?: 0
                    if (count < rentalCount) {
                        rentalMobileViewModel.plusRental(id)
                        bindingNonNull.rentalNum.text =
                            "대여 갯수:${currentRentalList()}"
                    } else {
                        showToast("최대 갯수 입니다.")
                    }

                }
            }
        rentalMobileAdapter.minusRentalMobileListener =
            object : RentalMobileAdapter.MinusRentalMobileListener {
                override fun onClick(id: Int) {
                    rentalMobileViewModel.minusRental(id)
                    bindingNonNull.rentalNum.text =
                        "대여 갯수:${currentRentalList()}"
                }
            }
        rentalMobileAdapter.updateRentalMobileListener =
            object : RentalMobileAdapter.UpdateRentalMobileListener {
                override fun onClick(id: Int) {
                    //TODO : 옵션 변경 기능
                }
            }
        bindingNonNull.rentalListRv.adapter = rentalMobileAdapter


        rentalMobileViewModel.rentalList.observe(viewLifecycleOwner) {
            bindingNonNull.rentalNum.text =
                "대여 갯수:${currentRentalList()}"
            rentalMobileAdapter.submitList(it)
        }

    }

    fun currentRentalList(): Int? {
        return rentalMobileViewModel.rentalList.value?.sumOf { it.count }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        rentalMobileViewModel.getAddressList()
        rentalMobileViewModel.getPossibleRentalCount()
        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }



    private fun rentalMobileUi() = with(bindingNonNull) {
        rentalNum.text = "대여 갯수:0"

        rentalMobileViewModel.possibleRentalCount.observe(viewLifecycleOwner) { count ->
            rentalCount = count
            rentalPossibleNum.text = "신청 가능 갯수:${count}개"
        }

        mobileAdd.setOnClickListener {
            val temp = currentRentalList() ?: 0
            if (temp == rentalCount) {
                showToast("최대 갯수 입니다.")
            } else {
                findNavController().navigate(
                    R.id.action_rentalMobileFragment_to_rentalAddFragment,
                )
            }
        }

        postRental.setOnClickListener {

            if (rentalMobileViewModel.rentalList.value?.size == 0) {
                showToast("1개 이상을 추가해주세요")
            } else {
                findNavController().navigate(
                    R.id.action_rentalMobileFragment_to_rentalPayFragment,
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
         * @return A new instance of fragment RentalMoblieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentalMobileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}