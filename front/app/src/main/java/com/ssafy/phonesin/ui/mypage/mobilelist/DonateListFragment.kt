package com.ssafy.phonesin.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.FragmentMyPageDonateListBinding
import com.ssafy.phonesin.model.mypage.MyDonate
import com.ssafy.phonesin.model.mypage.MyDonateToggle
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.mobilelist.MyDonateListAdapter
import com.ssafy.phonesin.ui.mypage.mobilelist.MyReturnListAdapter

class DonateListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageDonateListBinding
    private lateinit var rentalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageDonateListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRentalList()
    }

    private fun setRentalList() {
        rentalRecyclerView = binding.recyclerViewDonateList

        val donateList =
            listOf<MyDonateToggle>(
                MyDonateToggle(MyDonate(1, "2023/08/10"), false),
                MyDonateToggle(MyDonate(2, "2023/08/11"), false),
                MyDonateToggle(MyDonate(3, "2023/08/12"), false),
                MyDonateToggle(MyDonate(4, "2023/08/13"), false)
            )

        rentalRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rentalRecyclerView.adapter = MyDonateListAdapter(donateList)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}