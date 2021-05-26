package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("id_account")
    val id: Int?,
    val email: String,
    val password: String,
)
