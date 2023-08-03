package com.ssafy.phonesin.ui.mypage.mobilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemMyRentalListBinding
import com.ssafy.phonesin.model.mypage.MyRentalToggle

class MyRentalListAdapter(
    private val rentalList: List<MyRentalToggle>,
) :
    RecyclerView.Adapter<MyRentalListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMyRentalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rental: MyRentalToggle) = with(binding) {
            if (rental.rental.status == 1) {
                textViewMobileName.visibility = View.INVISIBLE
                textViewStateRegist.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (rental.rental.status == 2) {
                textViewStateApprove.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else if (rental.rental.status == 3) {
                textViewStateDelivery.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            } else {
                textViewStateRental.setTextColor(ContextCompat.getColor(itemView.context, R.color.keyColor1))
            }

            layoutToggleUp.setOnClickListener {
                rental.toggle = !rental.toggle

                if (rental.toggle) {
                    imageViewToggle.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                    dividerToggle.visibility = View.VISIBLE
                    layoutToggleDown.visibility =View.VISIBLE

                    if (rental.rental.status == 1) {
                        layoutProductNum.visibility = View.GONE
                        layoutRegist.visibility = View.VISIBLE
                    } else if (rental.rental.status == 2) {
                        layoutApprove.visibility = View.VISIBLE
                    } else if (rental.rental.status == 3) {
                        layoutDelivery.visibility = View.VISIBLE
                    } else {
                        layoutRental.visibility = View.VISIBLE
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
            ItemMyRentalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(rentalList[position])
    }

    override fun getItemCount() = rentalList.size
}