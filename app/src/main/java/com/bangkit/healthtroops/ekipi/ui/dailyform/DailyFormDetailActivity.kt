package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import com.bangkit.healthtroops.ekipi.databinding.ActivityDailyFormDetailBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemDetail
import com.bangkit.healthtroops.ekipi.ui.adapter.ItemDetailAdapter

class DailyFormDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyFormDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyFormDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formKipiDaily = intent.getParcelableExtra<FormKipiDailyResponse>(EXTRA_FORM_KIPI)
        val dayNumber = intent.getStringExtra(EXTRA_DAY_NUMBER)

        val adapterKipiData = ItemDetailAdapter()
        val adapterRecommendation = ItemDetailAdapter()

        if (formKipiDaily != null) {
            adapterKipiData.setData(
                listOf(
                    ItemDetail(
                        getString(R.string.symptom_list),
                        "ini gejala 1, gejala 2, dst"
                    ),
                    ItemDetail(
                        getString(R.string.symptom_feeling_description),
                        formKipiDaily.lainnya
                    ),
                )
            )

            adapterRecommendation.setData(
                listOf(
                    ItemDetail(
                        getString(R.string.severity),
                        "Sedang"
                    ),
                    ItemDetail(
                        getString(R.string.recommendation_for_symptom, "gejala 1"),
                        formKipiDaily.diagnosis
                    ),
                    ItemDetail(
                        getString(R.string.recommendation_for_symptom, "gejala 2"),
                        "lorem ipsum"
                    ),
                    ItemDetail(
                        getString(R.string.recommendation_for_symptom, "gejala 3"),
                        "lorem ipsum"
                    ),
                )
            )
        }

        binding.rvKipiData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterKipiData
        }

        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRecommendation
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = dayNumber
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_FORM_KIPI = "extra_form_kipi"
        const val EXTRA_DAY_NUMBER = "extra_day_number"
    }
}
