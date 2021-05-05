package com.bangkit.healthtroops.ekipi.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.databinding.RiwayatItemBinding

class RiwayatListAdapter: RecyclerView.Adapter<RiwayatListAdapter.Holder>() {
//    private val listItem = ArrayList<String>()
    private val listItem = listOf(
        "5/05/2021", "6/05/2021", "7/05/2021", "8/05/2021", "9/05/2021", "10/05/2021"
    )
//    fun setListItem(newList: ArrayList<String>?) {
//        newList?.apply {
//            listItem.clear()
//            listItem.addAll(this)
//            notifyDataSetChanged()
//        }
//    }

    inner class Holder(private val binding: RiwayatItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int, tanggal: String) {
            binding.tvTitle.text = "Hari ke-$index"
            binding.tvTanggal.text = tanggal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = RiwayatItemBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(position, listItem[position])

    override fun getItemCount(): Int = listItem.size
}