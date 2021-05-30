package com.bangkit.healthtroops.ekipi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.databinding.ItemSelectBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemSelect

class ItemSelectAdapter(private val items: List<ItemSelect>) :
    RecyclerView.Adapter<ItemSelectAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ListViewHolder(private val binding: ItemSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemSelect) {
            binding.apply {
                tvItemName.text = item.name
                item.color?.let { tvItemName.setTextColor(it) }
                tvItemDescription.text = item.description
                root.setOnClickListener(item.onClickListener)
            }
        }
    }
}
