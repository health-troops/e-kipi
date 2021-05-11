package com.bangkit.healthtroops.ekipi.model

data class LoginResponse(
    val status: Int,
    val error: String?,
    val response: List<Account>,
    val token: String,
)