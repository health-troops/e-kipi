package com.bangkit.healthtroops.ekipi.data

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id_account")
    val id: Int?,
    val email: String,
    val password: String,
)