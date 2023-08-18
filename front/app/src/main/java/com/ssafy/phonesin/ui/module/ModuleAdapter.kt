package com.ssafy.phonesin.ui.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemModuleBinding
import com.ssafy.phonesin.model.ModuleType
import com.ssafy.phonesin.ui.util.setDebouncingClickListener

class ModuleAdapter(
    private val items: List<ModuleType>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: ModuleType)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ModuleViewHolder(private val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModuleType) {
            with(binding) {
                cardView.setCardBackgroundColor(item.backgroundColor)
                moduleImage.setImageResource(item.imageResource)
                textViewModuleTitle.text = item.title
                textViewModuleContent.text = item.content

                linearLayoutModule.setDebouncingClickListener {
                    onItemClickListener.onItemClick(item)
                }
            }
        }
    }
}
