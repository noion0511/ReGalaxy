package com.ssafy.phonesin.ui.mypage.mobilelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.FragmentMyPageDonateListBinding
import com.ssafy.phonesin.model.UserDonation
import com.ssafy.phonesin.model.mypage.MyDonate
import com.ssafy.phonesin.model.mypage.MyDonateToggle
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.UserViewModel

class DonateListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageDonateListBinding
    private lateinit var donateRecyclerView: RecyclerView

    val userViewModel: UserViewModel by activityViewModels()

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
        donateRecyclerView = binding.recyclerViewDonateList

        userViewModel.getUserDonationList()
        donateRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        donateRecyclerView.adapter = MyDonateListAdapter(userViewModel.myDonationList, object : MyDonateListAdapter.OnCancelClickListener {
            override fun onCancelClick(donationId: Int) {
                userViewModel.cancelUserDonation(donationId)
                Toast.makeText(requireContext(), "신청이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        userViewModel.myDonationList.observe(viewLifecycleOwner, Observer {myDonationList ->
            donateRecyclerView.adapter?.notifyDataSetChanged()
        })
    }
}