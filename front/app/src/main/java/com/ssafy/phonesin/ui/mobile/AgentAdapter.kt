package com.ssafy.phonesin.ui.mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemAgentBinding
import com.ssafy.phonesin.model.AgentAddress

class AgentAdapter :
    ListAdapter<AgentAddress, AgentAdapter.AgentViewHolder>(AgentDiffCallback()) {
    private lateinit var binding: ItemAgentBinding

    inner class AgentViewHolder(private val binding: ItemAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(agentAddress: AgentAddress) {
            binding.apply {
                textViewItemAgentTitle.text = agentAddress.name
                textViewItemAgentAddress.text = agentAddress.address
                textViewItemAgentDistance.text = agentAddress.distance.toString() + "m"

                root.setOnClickListener {
                    detailAgentListener.onClick(layoutPosition)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AgentViewHolder {
        binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AgentViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    interface DetailAgentListener {
        fun onClick(id: Int)
    }

    lateinit var detailAgentListener: DetailAgentListener

}

class AgentDiffCallback : DiffUtil.ItemCallback<AgentAddress>() {
    override fun areItemsTheSame(oldItem: AgentAddress, newItem: AgentAddress): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: AgentAddress, newItem: AgentAddress): Boolean {
        return newItem.hashCode() == oldItem.hashCode()
    }
}