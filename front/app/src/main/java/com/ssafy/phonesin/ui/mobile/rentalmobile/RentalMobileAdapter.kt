package com.ssafy.phonesin.ui.mobile.rentalmobile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.R
import com.ssafy.phonesin.databinding.ItemRentalListBinding
import com.ssafy.phonesin.model.Rental
import com.ssafy.phonesin.model.RentalBody
import com.ssafy.phonesin.ui.util.Util.selectModule

class RentalMobileAdapter :
    ListAdapter<RentalBody, RentalMobileAdapter.RentalMobileViewHolder>(RentalMobileDiffCallback()) {
    private lateinit var binding: ItemRentalListBinding

    inner class RentalMobileViewHolder(private val binding: ItemRentalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rental: RentalBody) {
            binding.apply {
                textViewItemRentalDate.text = String.format(
                    root.context.resources.getString(R.string.date_list),
                    rental.usingDate
                )
                textViewItemRentalAddress.text = String.format(
                    root.context.resources.getString(R.string.address_list),
                    rental.rentalDeliveryLocation
                )
                textViewItemRentalPrice.text = String.format(
                    root.context.resources.getString(R.string.price_list),
                    rental.fund
                )
                textViewItemRentalFunction.text = String.format(
                    root.context.resources.getString(R.string.module_list),
                    selectModule(rental)
                )
                textViewRentalCount.text = rental.count.toString()

                imageViewItemRentalClose.setOnClickListener {
                    deleteRentalMobileListener.onClick(layoutPosition)

                }
                imageViewRentalPlus.setOnClickListener {
                    plusRentalMobileListener.onClick(layoutPosition)
                    textViewRentalCount.text = rental.count.toString()
                }

                imageViewRentalMinus.setOnClickListener {
                    minusRentalMobileListener.onClick(layoutPosition)
                    textViewRentalCount.text = rental.count.toString()
                }

                buttonChangeOption.setOnClickListener {
                    updateRentalMobileListener.onClick(layoutPosition)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalMobileViewHolder {
        binding = ItemRentalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RentalMobileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RentalMobileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface UpdateRentalMobileListener {
        fun onClick(id: Int)
    }

    lateinit var updateRentalMobileListener: UpdateRentalMobileListener

    interface DeleteRentalMobileListener {
        fun onClick(id: Int)
    }

    lateinit var deleteRentalMobileListener: DeleteRentalMobileListener

    interface PlusRentalMobileListener {
        fun onClick(id: Int)
    }

    lateinit var plusRentalMobileListener: PlusRentalMobileListener

    interface MinusRentalMobileListener {
        fun onClick(id: Int)
    }

    lateinit var minusRentalMobileListener: MinusRentalMobileListener

}

class RentalMobileDiffCallback : DiffUtil.ItemCallback<RentalBody>() {
    override fun areItemsTheSame(oldItem: RentalBody, newItem: RentalBody): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: RentalBody, newItem: RentalBody): Boolean {
        return newItem.hashCode() == oldItem.hashCode()
    }
}