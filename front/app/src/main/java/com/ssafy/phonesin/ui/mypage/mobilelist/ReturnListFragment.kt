package com.ssafy.phonesin.ui.mypage.mobilelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.FragmentMyPageReturnListBinding
import com.ssafy.phonesin.model.mypage.MyReturn
import com.ssafy.phonesin.model.mypage.MyReturnToggle
import com.ssafy.phonesin.ui.MainActivity

class ReturnListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageReturnListBinding
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
        binding = FragmentMyPageReturnListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRentalList()
    }

    private fun setRentalList() {
        rentalRecyclerView = binding.recyclerViewReturnList

        val returnList =
            listOf<MyReturnToggle>(
                MyReturnToggle(MyReturn(1, "Galaxy 1"), false),
                MyReturnToggle(MyReturn(2, "Galaxy 2"), false),
                MyReturnToggle(MyReturn(3, "Galaxy 3"), false),
                MyReturnToggle(MyReturn(4, "Galaxy 4"), false)
            )

        rentalRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rentalRecyclerView.adapter = MyReturnListAdapter(returnList)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}