package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.FragmentMyPageModifyInfoBinding
import com.ssafy.phonesin.model.UserModify
import com.ssafy.phonesin.ui.MainActivity
import com.ssafy.phonesin.ui.mypage.UserViewModel
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class ModifyInfoFragment : Fragment() {
    private lateinit var binding: FragmentMyPageModifyInfoBinding
    private lateinit var addressRecyclerView: RecyclerView

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
        binding = FragmentMyPageModifyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.my_page)
        }
        setInfo()
        setOnClick()
        setRegistedAddress()
    }

    private fun setInfo() = with(binding) {
        editTextName.setText(userViewModel.user.value?.memberName ?: "")
        editTextPhoneNumber.setText(userViewModel.user.value?.phoneNumber ?: "")
    }

    private fun setOnClick() = with(binding) {

        buttonSaveInfo.setDebouncingClickListener {
            val newInfo = UserModify(userViewModel.user.value?.isCha ?: false, editTextName.text.toString(), editTextPhoneNumber.text.toString())
            userViewModel.updateUserInfo(newInfo)
            Log.d("modifyInfo", "setOnClick: ${newInfo}")
            Toast.makeText(requireContext(), "사용자 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.my_page)
        }

        layoutAddAddress.setDebouncingClickListener {
            findNavController().navigate(R.id.addAddressFragment)
        }
    }

    private fun setRegistedAddress() {
        addressRecyclerView = binding.recyclerViewAddress

        userViewModel.getAddress()
        addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        addressRecyclerView.adapter = RegistedAddressAdapter(userViewModel.addressList, object : RegistedAddressAdapter.OnRemoveClickListener {
            override fun onRemoveClick(addressId: Int) {
                userViewModel.removeAddress(addressId)
                Toast.makeText(requireContext(), "주소가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        userViewModel.addressList.observe(viewLifecycleOwner, Observer {addressList ->
            addressRecyclerView.adapter?.notifyDataSetChanged()
        })
    }
}