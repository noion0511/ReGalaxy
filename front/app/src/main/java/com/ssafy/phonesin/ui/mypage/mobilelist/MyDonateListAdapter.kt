package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyDonateListBinding
import com.ssafy.phonesin.model.UserDonation
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class MyDonateListAdapter(
    private val donateList: MutableLiveData<List<UserDonation>>,
    private val onCancelClickListener: OnCancelClickListener
) :
    RecyclerView.Adapter<MyDonateListAdapter.ViewHolder>() {

    interface OnCancelClickListener {
        fun onCancelClick(donationId: Int)
    }

    inner class ViewHolder(private val binding: ItemMyDonateListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donate: UserDonation) = with(binding) {
            textViewDonateDate.setText(donate.donationCreatedAt.substring(2, donate.donationCreatedAt.lastIndex + 1))

            toggleState(donate)

            if (donate.donationStatus == 1) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDone.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                
                buttonCancelRegist.setDebouncingClickListener {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                    dividerToggle.visibility = View.GONE
                    layoutToggleDown.visibility = View.GONE
                    onCancelClickListener.onCancelClick(donate.donationId)
                }
            } else if (donate.donationStatus == 2) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDone.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))

                textViewPickupDate.setText(donate.donationDeliveryDate)
                textViewPickupAddress.setText(donate.donationDeliveryLocation)
            } else if (donate.donationStatus == 3) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateDone.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            } else {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDone.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setDebouncingClickListener {
                donate.toggle = !donate.toggle
                toggleState(donate)
            }
        }

        fun toggleState(donate: UserDonation) = with(binding) {
            if (donate.toggle) {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                dividerToggle.visibility = View.VISIBLE
                layoutToggleDown.visibility = View.VISIBLE

                if (donate.donationStatus == 1) {
                    layoutRegist.visibility = View.VISIBLE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.GONE
                    layoutDone.visibility = View.GONE
                } else if (donate.donationStatus == 2) {
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.VISIBLE
                    layoutPickup.visibility = View.GONE
                    layoutDone.visibility = View.GONE
                } else if (donate.donationStatus == 3) {
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.VISIBLE
                    layoutDone.visibility = View.GONE
                } else {
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.GONE
                    layoutDone.visibility = View.VISIBLE
                }
            } else {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                dividerToggle.visibility = View.GONE
                layoutToggleDown.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyDonateListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        donateList.value?.let { viewHolder.bind(it.get(position)) }
    }

    override fun getItemCount() = donateList.value?.size ?: 0
}