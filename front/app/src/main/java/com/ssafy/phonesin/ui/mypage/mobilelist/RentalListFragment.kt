package com.ssafy.phonesin.ui.mypage.mobilelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.FragmentMyPageRentalListBinding
import com.ssafy.phonesin.model.mypage.MyRental
import com.ssafy.phonesin.model.mypage.MyRentalToggle
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.UserViewModel

class RentalListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageRentalListBinding
    private lateinit var rentalRecyclerView: RecyclerView

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
        binding = FragmentMyPageRentalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRentalList()
    }

    private fun setRentalList() {
        rentalRecyclerView = binding.recyclerViewRentalList

        userViewModel.getUserRental()
        rentalRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rentalRecyclerView.adapter = MyRentalListAdapter(userViewModel.myRentalList, object : MyRentalListAdapter.OnCancelClickListener {
            override fun onCancelClick(rentalId: Int) {
                userViewModel.cancelUserRental(rentalId)
                Toast.makeText(requireContext(), "신청이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        userViewModel.myRentalList.observe(viewLifecycleOwner, Observer {myRentalList ->
            rentalRecyclerView.adapter?.notifyDataSetChanged()
        })
    }
}