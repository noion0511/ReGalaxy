package com.ssafy.phonesin.ui.module

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.phonesin.databinding.ItemModuleBinding
import com.ssafy.phonesin.model.ModuleType

class ModuleAdapter(private val items: List<ModuleType>) :
    RecyclerView.Adapter<ModuleAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CustomViewHolder(private val binding: ItemModuleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ModuleType) {
            with(binding) {
                cardView?.setCardBackgroundColor(item.backgroundColor)
                moduleImage?.setImageResource(item.imageResource)
                textViewModuleTitle?.text = item.title
                textViewModuleContent?.text = item.content
            }
        }
    }
}
