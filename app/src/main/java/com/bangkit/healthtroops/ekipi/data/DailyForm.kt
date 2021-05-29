package com.bangkit.healthtroops.ekipi.data

import com.google.gson.annotations.SerializedName

data class DailyForm(
	val idAccount: Int,
	val diagnosis: String,
	val checklist: List<Int>,
	val tanggal: String,
	@SerializedName("PredictionClass0")
	val predictionClass0: Double,
	@SerializedName("PredictionClass1")
    val predictionClass1: Double,
	@SerializedName("PredictionClass2")
	val predictionClass2: Double,
	val lainnya: String,
	@SerializedName("Recommendation")
	val recommendation: String
)

