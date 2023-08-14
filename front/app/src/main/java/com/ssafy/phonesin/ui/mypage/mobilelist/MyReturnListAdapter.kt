package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyReturnListBinding
import com.ssafy.phonesin.model.UserReturn

class MyReturnListAdapter(
    private val returnList: MutableLiveData<List<UserReturn>>,
    private val onCancelClickListener: OnCancelClickListener
) :
    RecyclerView.Adapter<MyReturnListAdapter.ViewHolder>() {

    interface OnCancelClickListener {
        fun onCancelClick(returnId: Int)
    }

    inner class ViewHolder(private val binding: ItemMyReturnListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(returns: UserReturn) = with(binding) {
            textViewMobileName.text = returns.modelName
            textViewProductNum.text = returns.phoneId.toString()
            toggleState(returns)

            if (returns.backStatus == 1) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateConfirm.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))

                buttonCancelRegist.setOnClickListener {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                    dividerToggle.visibility = View.GONE
                    layoutToggleDown.visibility =View.GONE
                    onCancelClickListener.onCancelClick(returns.backId)
                }
            } else if (returns.backStatus == 2) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateConfirm.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            } else if (returns.backStatus == 3) {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateConfirm.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            } else {
                // 상태 확인
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStatePickup.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateConfirm.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setOnClickListener {
                returns.toggle = !returns.toggle
                toggleState(returns)
            }
        }

        fun toggleState(returns: UserReturn) = with(binding) {
            if (returns.toggle) {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                dividerToggle.visibility = View.VISIBLE
                layoutToggleDown.visibility =View.VISIBLE

                if (returns.backStatus == 1) {
                    layoutRegist.visibility = View.VISIBLE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.GONE
                    layoutConfirm.visibility = View.GONE
                } else if (returns.backStatus == 2) {
                    textViewPickupDate.text = returns.backDeliveryDate
                    textViewPickupAddress.text = returns.backDeliveryLocation

                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.VISIBLE
                    layoutPickup.visibility = View.GONE
                    layoutConfirm.visibility = View.GONE
                } else if (returns.backStatus == 3) {
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.VISIBLE
                    layoutConfirm.visibility = View.GONE
                } else {
                    if (returns.phoneStatus) {
                        textViewConfirmGuide.text = "정상작동이 확인되었습니다. 이용해주셔서 감사합니다."
                    } else {
                        textViewConfirmGuide.text = "파손으로 인한 보증금 차감이 안내될 예정입니다. 이용해주셔서 감사합니다."
                    }

                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutPickup.visibility = View.GONE
                    layoutConfirm.visibility = View.VISIBLE
                }
            } else {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                dividerToggle.visibility = View.GONE
                layoutToggleDown.visibility =View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyReturnListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        returnList.value?.let { viewHolder.bind(it.get(position)) }
    }

    override fun getItemCount() = returnList.value?.size ?: 0
}