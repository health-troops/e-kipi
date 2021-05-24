package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.data.ComorbidSymptom
import com.bangkit.healthtroops.ekipi.databinding.ItemComorbidCheckboxBinding

class ComorbidListAdapter : RecyclerView.Adapter<ComorbidListAdapter.ListViewHolder>() {

    private val symptoms = ArrayList<ComorbidSymptom>()
    val values = ArrayList<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemComorbidCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(symptoms[position], position)
    }

    override fun getItemCount(): Int {
        return symptoms.size
    }

    fun setData(items: List<ComorbidSymptom>) {
        symptoms.clear()
        symptoms.addAll(items)
        values.clear()
        for (x in 1..itemCount) {
            values.add(false)
        }

        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemComorbidCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symptom: ComorbidSymptom, position: Int) {
            binding.chkComorbid.text = symptom.name
            binding.chkComorbid.setOnCheckedChangeListener { _, b ->
                values[position] = b
            }
        }
    }
}
