package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class FormKipiDailyResponse(
    val id: Int,
    @SerializedName("id_account")
    val idAccount: Int,
    val tanggal: Date,
    val lainnya: String,
    val diagnosis: String,
    @SerializedName("PredictionClass0")
    val prediction0: Float,
    @SerializedName("PredictionClass1")
    val prediction1: Float,
    @SerializedName("PredictionClass2")
    val prediction2: Float,
    @SerializedName("Recommendation")
    val recommendation: String,
)
