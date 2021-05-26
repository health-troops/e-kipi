package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.healthtroops.ekipi.data.ComorbidSymptom
import com.bangkit.healthtroops.ekipi.databinding.ItemComorbidCheckboxBinding
import com.bangkit.healthtroops.ekipi.domain.model.ComorbidData
import okhttp3.internal.immutableListOf

class ComorbidListAdapter : RecyclerView.Adapter<ComorbidListAdapter.ListViewHolder>() {

    private val symptoms = ArrayList<ComorbidSymptom>()
    val values = ArrayList<Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemComorbidCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(position)
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

    fun setValues(data: ComorbidData) {
        Log.d(TAG, "setValues: $data")
        values.clear()
        values.addAll(
            immutableListOf(
                data.hipertensi,
                data.diabetesMelitus,
                data.gagalJantung,
                data.jantungKoroner,
                data.paruObstruktifKronis,
                data.asma,
                data.hati,
                data.tbc,
                data.autoimun,
                data.kanker,
                data.hiv,
                data.alergiObat,
                data.kelainanDarah,
                data.hipertiroid,
                data.ginjal,
                data.dermatitisAtopi,
                data.reaksiAnafilaksis,
                data.urtikaria,
                data.alergiMakanan,
                data.interstitialLung,
            )
        )
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemComorbidCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val symptom = symptoms[position]
            val value = values[position]

            binding.chkComorbid.text = symptom.name
            binding.chkComorbid.isChecked = value
            binding.chkComorbid.setOnCheckedChangeListener { _, b ->
                values[position] = b
            }
        }
    }

    companion object {
        private const val TAG = "ComorbidListAdapter"
    }
}
