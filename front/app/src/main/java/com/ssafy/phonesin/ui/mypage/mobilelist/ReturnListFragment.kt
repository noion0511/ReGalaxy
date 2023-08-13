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
import com.ssafy.phonesin.databinding.FragmentMyPageReturnListBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.UserViewModel

class ReturnListFragment : Fragment() {
    private lateinit var binding: FragmentMyPageReturnListBinding
    private lateinit var returnRecyclerView: RecyclerView

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
        binding = FragmentMyPageReturnListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRentalList()
    }

    private fun setRentalList() {
        returnRecyclerView = binding.recyclerViewReturnList

        returnRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        returnRecyclerView.adapter = MyReturnListAdapter(userViewModel.myReturnList, object : MyReturnListAdapter.OnCancelClickListener {
            override fun onCancelClick(returnId: Int) {
                userViewModel.cancelUserReturn(returnId)
                Toast.makeText(requireContext(), "신청이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        userViewModel.myReturnList.observe(viewLifecycleOwner, Observer {myReturnList ->
            returnRecyclerView.adapter?.notifyDataSetChanged()
        })

    }
}