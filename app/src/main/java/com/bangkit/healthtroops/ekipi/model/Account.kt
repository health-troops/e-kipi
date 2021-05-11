package com.bangkit.healthtroops.ekipi.model

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id_account")
    val id: Int?,
    val email: String,
    val password: String,
)