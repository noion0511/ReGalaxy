package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyRentalListBinding
import com.ssafy.phonesin.model.UserRental
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class MyRentalListAdapter(
    private val rentalList: MutableLiveData<List<UserRental>>,
    private val onCancelClickListener: OnCancelClickListener
) :
    RecyclerView.Adapter<MyRentalListAdapter.ViewHolder>() {

    interface OnCancelClickListener {
        fun onCancelClick(rentalId: Int)
    }

    inner class ViewHolder(private val binding: ItemMyRentalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rental: UserRental) = with(binding) {
            textViewMobileName.text = rental.modelName
            textViewRentalDate.text = rental.applyDate.substring(0, 10)
            textViewRentalCost.text = rental.fund.toString()
            textViewProductNum.text = rental.phoneId?.toString() ?: ""

            toggleState(rental)

            if (rental.rentalStatus == 1) {
                // 상태 확인
                textViewMobileName.visibility = View.INVISIBLE
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDelivery.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateRental.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))

                buttonCancelRegist.setDebouncingClickListener {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                    dividerToggle.visibility = View.GONE
                    layoutToggleDown.visibility =View.GONE
                    onCancelClickListener.onCancelClick(rental.rentalId)
                }
            } else if (rental.rentalStatus == 2) {
                textViewRentalFunction.text = rentalFunction(rental)
                textViewRentalAddress.text = rental.rentalDeliveryLocation
                // 상태 확인
                textViewMobileName.visibility = View.VISIBLE
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateDelivery.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateRental.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            } else if (rental.rentalStatus == 3) {
                // 상태 확인
                textViewMobileName.visibility = View.VISIBLE
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDelivery.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
                textViewStateRental.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
            } else {
                textViewRentalStartDate.text = rental.rentalStart.substring(0, 10)
                textViewRentalEndDate.text = rental.rentalEnd.substring(0, 10)
                // 상태 확인
                textViewMobileName.visibility = View.VISIBLE
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateDelivery.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                textViewStateRental.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setDebouncingClickListener {
                rental.toggle = !rental.toggle
                toggleState(rental)
            }
        }

        fun toggleState(rental: UserRental) = with(binding) {
            if (rental.toggle) {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                dividerToggle.visibility = View.VISIBLE
                layoutToggleDown.visibility =View.VISIBLE

                if (rental.rentalStatus == 1) {
                    layoutProductNum.visibility = View.GONE
                    layoutRegist.visibility = View.VISIBLE
                    layoutApprove.visibility = View.GONE
                    layoutDelivery.visibility = View.GONE
                    layoutRental.visibility = View.GONE
                } else if (rental.rentalStatus == 2) {
                    layoutProductNum.visibility = View.VISIBLE
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.VISIBLE
                    layoutDelivery.visibility = View.GONE
                    layoutRental.visibility = View.GONE
                } else if (rental.rentalStatus == 3) {
                    layoutProductNum.visibility = View.VISIBLE
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutDelivery.visibility = View.VISIBLE
                    layoutRental.visibility = View.GONE
                } else {
                    layoutProductNum.visibility = View.VISIBLE
                    layoutRegist.visibility = View.GONE
                    layoutApprove.visibility = View.GONE
                    layoutDelivery.visibility = View.GONE
                    layoutRental.visibility = View.VISIBLE
                }
            } else {
                imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                dividerToggle.visibility = View.GONE
                layoutToggleDown.visibility =View.GONE
            }
        }

        fun rentalFunction(rental: UserRental) : String {
            var function = ""
            if (rental.y2K) function += "사진"
            if (rental.homecam) function += "홈캠"
            if (rental.climateHumidity) function += "온도계"
            val stringBuilder = StringBuilder(function)

            if (stringBuilder.length == 4) {
                stringBuilder.setCharAt(2, ' ')
                stringBuilder.setCharAt(2, '/')
                stringBuilder.setCharAt(2, ' ')
            } else if (stringBuilder.length == 7) {
                stringBuilder.setCharAt(4, ' ')
                stringBuilder.setCharAt(4, '/')
                stringBuilder.setCharAt(4, ' ')
            }
            return stringBuilder.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyRentalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        rentalList.value?.let { viewHolder.bind(it.get(position)) }
    }

    override fun getItemCount() = rentalList.value?.size ?: 0
}