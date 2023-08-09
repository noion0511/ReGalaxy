package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageModifyInfoBinding
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.ui.MainActivity

class ModifyInfoFragment : Fragment() {
    private lateinit var binding: FragmentMyPageModifyInfoBinding
    private lateinit var addressRecyclerView: RecyclerView

    val addressViewModel: AddressViewModel by activityViewModels()

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

        addressViewModel.getAddress()
        addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        addressRecyclerView.adapter = RegistedAddressAdapter(addressViewModel.addressList, object : RegistedAddressAdapter.OnRemoveClickListener {
            override fun onRemoveClick(addressId: Int) {
                addressViewModel.removeAddress(addressId)
                Toast.makeText(requireContext(), "주소가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        addressViewModel.addressList.observe(viewLifecycleOwner, Observer {addressList ->
            addressRecyclerView.adapter?.notifyDataSetChanged()
        })
    }
}