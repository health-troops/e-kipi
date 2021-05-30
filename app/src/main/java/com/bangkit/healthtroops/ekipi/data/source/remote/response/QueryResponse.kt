package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class QueryResponse<T>(
    val status: Int,
    val error: String?,
    @SerializedName("response")
    val data: List<T>,
)
