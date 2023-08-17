package com.ssafy.phonesin.ui.mypage.modifyinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemAddressListBinding
import com.ssafy.phonesin.model.Address
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class RegistedAddressAdapter(
    private val addressList: MutableLiveData<List<Address>>,
    private val onRemoveClickListener: OnRemoveClickListener
) :
    RecyclerView.Adapter<RegistedAddressAdapter.ViewHolder>() {

    interface OnRemoveClickListener {
        fun onRemoveClick(addressId: Int)
    }

    inner class ViewHolder(private val binding: ItemAddressListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.textViewRegistedAddress.setText(address.address)

            binding.imageViewRemoveAddress.setDebouncingClickListener {
                onRemoveClickListener.onRemoveClick(address.addressId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        addressList.value?.let { viewHolder.bind(it.get(position)) }
    }

    override fun getItemCount() = addressList.value?.size ?: 0
}

