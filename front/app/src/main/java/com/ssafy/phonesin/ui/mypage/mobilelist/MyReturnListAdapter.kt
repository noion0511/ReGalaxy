package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyReturnListBinding
import com.ssafy.phonesin.model.mypage.MyReturnToggle

class MyReturnListAdapter(
    private val returnList: List<MyReturnToggle>,
) :
    RecyclerView.Adapter<MyReturnListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyReturnListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(returns: MyReturnToggle) = with(binding) {
            textViewMobileName.text = returns.donate.phoneNmae

            if (returns.donate.status == 1) {
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (returns.donate.status == 2) {
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (returns.donate.status == 3) {
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else {
                textViewStateConfirm.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setOnClickListener {
                returns.toggle = !returns.toggle

                if (returns.toggle) {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                    dividerToggle.visibility = View.VISIBLE
                    layoutToggleDown.visibility =View.VISIBLE

                    if (returns.donate.status == 1) {
                        layoutRegist.visibility = View.VISIBLE
                    } else if (returns.donate.status == 2) {
                        layoutApprove.visibility = View.VISIBLE
                    } else if (returns.donate.status == 3) {
                        layoutPickup.visibility = View.VISIBLE
                    } else {
                        layoutConfirm.visibility = View.VISIBLE
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
            ItemMyReturnListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(returnList[position])
    }

    override fun getItemCount() = returnList.size
}