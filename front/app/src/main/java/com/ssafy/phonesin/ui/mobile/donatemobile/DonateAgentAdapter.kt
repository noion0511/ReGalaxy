package com.ssafy.phonesin.ui.mobile.donatemobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemAgentBinding
import com.ssafy.phonesin.model.AgentAddress

class DonateAgentAdapter :
    ListAdapter<AgentAddress, DonateAgentAdapter.DonateAgentViewHolder>(DonateAgentDiffCallback()) {
    private lateinit var binding: ItemAgentBinding

    inner class DonateAgentViewHolder(private val binding: ItemAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agentAddress: AgentAddress) {
            binding.apply {
                textViewItemAgentTitle.text = agentAddress.name
                textViewItemAgentAddress.text = agentAddress.address
                textViewItemAgentDistance.text = agentAddress.distance.toString() + "m"

                root.setOnClickListener {
                    detailDonateAgentListener.onClick(layoutPosition)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonateAgentAdapter.DonateAgentViewHolder {
        binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonateAgentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DonateAgentAdapter.DonateAgentViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    interface DetailDonateAgentListener {
        fun onClick(id: Int)
    }

    lateinit var detailDonateAgentListener: DetailDonateAgentListener

}

class DonateAgentDiffCallback : DiffUtil.ItemCallback<AgentAddress>() {
    override fun areItemsTheSame(oldItem: AgentAddress, newItem: AgentAddress): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: AgentAddress, newItem: AgentAddress): Boolean {
        return newItem.hashCode() == oldItem.hashCode()
    }
}