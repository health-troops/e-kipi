package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MLResponse(

	@field:SerializedName("treatment")
	val treatment: List<TreatmentItem>,

	@field:SerializedName("prediction")
	val prediction: List<Double>,

	@field:SerializedName("recommendation")
	val recommendation: Recommendation
)

data class TreatmentItem(

	@field:SerializedName("penanganan")
	val penanganan: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gejala")
	val gejala: String
)

data class Recommendation(

	@field:SerializedName("level")
	val level: Int,

	@field:SerializedName("rekomendasi")
	val rekomendasi: String
)
