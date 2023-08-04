package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyDonateListBinding
import com.ssafy.phonesin.model.mypage.MyDonateToggle

class MyDonateListAdapter(
    private val donateList: List<MyDonateToggle>,
) :
    RecyclerView.Adapter<MyDonateListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyDonateListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donate: MyDonateToggle) = with(binding) {
            textViewDonateDate.text = donate.donate.donateDate

            if (donate.donate.status == 1) {
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (donate.donate.status == 2) {
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (donate.donate.status == 3) {
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else {
                textViewStateDone.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setOnClickListener {
                donate.toggle = !donate.toggle

                if (donate.toggle) {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                    dividerToggle.visibility = View.VISIBLE
                    layoutToggleDown.visibility =View.VISIBLE

                    if (donate.donate.status == 1) {
                        layoutRegist.visibility = View.VISIBLE
                    } else if (donate.donate.status == 2) {
                        layoutApprove.visibility = View.VISIBLE
                    } else if (donate.donate.status == 3) {
                        layoutPickup.visibility = View.VISIBLE
                    } else {
                        layoutDone.visibility = View.VISIBLE
                    }
                } else {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                    dividerToggle.visibility = View.GONE
                    layoutToggleDown.visibility =View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyDonateListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(donateList[position])
    }

    override fun getItemCount() = donateList.size
}