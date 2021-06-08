package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.databinding.ActivityDailyFormDetailBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemDetail
import com.bangkit.healthtroops.ekipi.ui.adapter.ItemDetailAdapter

class DailyFormDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyFormDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyFormDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterKipiData = ItemDetailAdapter()
        adapterKipiData.setData(
            listOf(
                ItemDetail(
                    "Daftar Gejala",
                    "ini gejala 1, gejala 2, dst"
                ),
                ItemDetail(
                    "Deskripsi yang dirasakan",
                    "Saya tidak apa-apa."
                ),
            )
        )

        binding.rvKipiData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterKipiData
        }

        val adapterRecommendation = ItemDetailAdapter()
        adapterRecommendation.setData(
            listOf(
                ItemDetail(
                    "Tingkat keparahan",
                    "Sedang"
                ),
                ItemDetail(
                    "Rekomendasi untuk gejala 1",
                    "lorem ipsum"
                ),
                ItemDetail(
                    "Rekomendasi untuk gejala 2",
                    "lorem ipsum"
                ),
                ItemDetail(
                    "Rekomendasi untuk gejala 3",
                    "lorem ipsum"
                ),
            )
        )

        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecommendation
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Hari ke-1"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
