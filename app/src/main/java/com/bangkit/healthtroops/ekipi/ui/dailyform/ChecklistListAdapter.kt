package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.databinding.ItemComorbidCheckboxBinding
import com.bangkit.healthtroops.ekipi.domain.model.FormChecklist

class ChecklistListAdapter: RecyclerView.Adapter<ChecklistListAdapter.ListViewHolder>() {
    private var listData = ArrayList<FormChecklist>()
    val checked  = ArrayList<Int>()

    fun setList(list: List<FormChecklist>) {
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemComorbidCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FormChecklist) {
            binding.root.text = data.nama
            binding.root.setOnCheckedChangeListener { _, b ->
                if (b) checked.add(data.id)
                else checked.remove(data.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemComorbidCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}
