package com.ssafy.phonesin.ui.mypage.modifyinfo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageModifyInfoBinding
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.mobilelist.RegistedAddressAdapter

class ModifyInfoFragment : Fragment() {
    private lateinit var binding: FragmentMyPageModifyInfoBinding
    private lateinit var addressRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.hideBottomNavi(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageModifyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
        setRegistedAddress()
    }

    private fun setOnClick() = with(binding) {

        buttonSaveInfo.setOnClickListener {
            findNavController().navigate(R.id.my_page)
        }

        layoutAddAddress.setOnClickListener {
            findNavController().navigate(R.id.addAddressFragment)
        }
    }

    private fun setRegistedAddress() {
        addressRecyclerView = binding.recyclerViewAddress

        val addressList =
            listOf<String>("구미시 진평동 11", "구미시 진평동 12", "구미시 진평동 13", "구미시 진평동 14", "구미시 진평동 15")
        addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        addressRecyclerView.adapter = RegistedAddressAdapter(addressList, object : RegistedAddressAdapter.OnRemoveClickListener {
            override fun onRemoveClick(address: String) {
                Toast.makeText(requireContext(), "${address}가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModifyInfoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}