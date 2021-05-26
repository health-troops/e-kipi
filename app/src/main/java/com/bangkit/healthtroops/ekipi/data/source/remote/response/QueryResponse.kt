package com.bangkit.healthtroops.ekipi.data.source.remote.response

data class QueryResponse<T>(
    val status: Int,
    val error: String?,
    val response: List<T>,
)
