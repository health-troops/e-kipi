package com.bangkit.healthtroops.ekipi.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_account")
    var accountId: Int,
    @SerializedName("nama")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("tempat_lahir")
    val placeOfBirth: String,
    @SerializedName("tanggal_lahir")
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
