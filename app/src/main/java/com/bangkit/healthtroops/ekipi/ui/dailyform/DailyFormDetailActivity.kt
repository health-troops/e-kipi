package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.Resource
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import com.bangkit.healthtroops.ekipi.databinding.ActivityDailyFormDetailBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemDetail
import com.bangkit.healthtroops.ekipi.ui.adapter.ItemDetailAdapter
import com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel.DailyFormDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFormDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyFormDetailBinding
    private val viewModel by viewModels<DailyFormDetailViewModel>()

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
                        getString(R.string.placeholder)
                    ),
                    ItemDetail(
                        getString(R.string.symptom_feeling_description),
                        formKipiDaily.lainnya
                    ),
                )
            )

            adapterRecommendation.setData(getRecommendations(formKipiDaily))

            viewModel.fetchSymptomNames(formKipiDaily.id)
            viewModel.getSymptomNames().observe(this) {
                when (it) {
                    is Resource.Success -> {
                        adapterKipiData.setData(
                            listOf(
                                ItemDetail(
                                    getString(R.string.symptom_list),
                                    it.data.joinToString(", ")
                                ),
                                ItemDetail(
                                    getString(R.string.symptom_feeling_description),
                                    formKipiDaily.lainnya
                                ),
                            )
                        )
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                    }
                }
            }
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

    private fun getRecommendations(formKipiDaily: FormKipiDailyResponse): List<ItemDetail> {
        val list = mutableListOf(
            ItemDetail(
                getString(R.string.severity),
                formKipiDaily.recommendation
            )
        )

        val separated = formKipiDaily.diagnosis.split("\n\n")
        for (s in separated) {
            list.add(toItemDetail(s))
        }
        return list
    }

    private fun toItemDetail(data: String): ItemDetail {
        val separated = data.split("\n")
        return when (separated.size) {
            0 -> {
                ItemDetail("", "")
            }
            1 -> {
                ItemDetail(getString(R.string.recommendation_for_symptom, "gejala 1"), separated[0])
            }
            else -> {
                ItemDetail(
                    getString(R.string.recommendation_for_symptom, separated[0]),
                    separated[1]
                )
            }
        }
    }

    companion object {
        const val EXTRA_FORM_KIPI = "extra_form_kipi"
        const val EXTRA_DAY_NUMBER = "extra_day_number"
    }
}
