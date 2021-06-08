package com.bangkit.healthtroops.ekipi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.databinding.ItemDetailBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemDetail

class ItemDetailAdapter : RecyclerView.Adapter<ItemDetailAdapter.ListViewHolder>() {

    private val items = mutableListOf<ItemDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(data: List<ItemDetail>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemDetail) {
            binding.apply {
                tvItemLabel.text = item.label
                tvItemData.text = item.data
            }
        }
    }
}
