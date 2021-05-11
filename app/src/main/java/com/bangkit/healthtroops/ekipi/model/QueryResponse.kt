package com.bangkit.healthtroops.ekipi.model

data class QueryResponse<T>(
    val status: Int,
    val error: String?,
    val response: List<T>,
)