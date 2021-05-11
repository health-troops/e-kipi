package com.bangkit.healthtroops.ekipi.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_account")
    val accountId: Int,
    @SerializedName("nama")
    val name: String,
    val gender: String,
    val ttl: String,
    @SerializedName("no_hp")
    val noHp: String,
    @SerializedName("nama_ibu")
    val mothersName: String,
    @SerializedName("nama_ayah")
    val fathersName: String,
    @SerializedName("provinsi")
    val province: String,
    @SerializedName("kota")
    val city: String,
    @SerializedName("kec")
    val district: String,
    @SerializedName("kel")
    val subDistrict: String,
    @SerializedName("alamat")
    val address: String,
)