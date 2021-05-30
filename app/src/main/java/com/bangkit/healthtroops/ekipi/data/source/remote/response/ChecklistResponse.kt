package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChecklistResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_gejala")
    val nama: String,
    @SerializedName("penanganan")
    val penanganan: String
)