package com.ssafy.phonesin.ui.mypage.mobilelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.FragmentMyPageRentalListBinding
import com.ssafy.phonesin.model.mypage.MyRental
import com.ssafy.phonesin.model.mypage.MyRentalToggle
import com.ssafy.phonesin.ui.MainActivity

class RentalListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageRentalListBinding
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
        binding = FragmentMyPageRentalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRentalList()
    }

    private fun setRentalList() {
        rentalRecyclerView = binding.recyclerViewRentalList

        val rentalList =
            listOf<MyRentalToggle>(
                MyRentalToggle(MyRental(1, "Galaxy 1"), false),
                MyRentalToggle(MyRental(2, "Galaxy 2"), false),
                MyRentalToggle(MyRental(3, "Galaxy 3"), false),
                MyRentalToggle(MyRental(4, "Galaxy 4"), false)
            )

        rentalRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rentalRecyclerView.adapter = MyRentalListAdapter(rentalList)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RentalListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}