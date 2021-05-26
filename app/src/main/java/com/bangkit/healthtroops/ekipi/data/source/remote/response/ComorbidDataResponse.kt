package com.bangkit.healthtroops.ekipi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ComorbidDataResponse(
    @SerializedName("id_account")
    var idAccount: Int,
    var hipertensi: Int,
    @SerializedName("diabetes_melitus")
    var diabetesMelitus: Int,
    @SerializedName("gagal_jantung")
    var gagalJantung: Int,
    @SerializedName("jantung_koroner")
    var jantungKoroner: Int,
    @SerializedName("paru_obstruktif_knonis")
    var paruObstruktifKronis: Int,
    var asma: Int,
    var hati: Int,
    var tbc: Int,
    var autoimun: Int,
    var kanker: Int,
    var hiv: Int,
    @SerializedName("alergi_obat")
    var alergiObat: Int,
    @SerializedName("kelainan_darah")
    var kelainanDarah: Int,
    var hipertiroid: Int,
    var ginjal: Int,
    @SerializedName("dermatitis_atopi")
    var dermatitisAtopi: Int,
    @SerializedName("reaksi_anafilaksis")
    var reaksiAnafilaksis: Int,
    var urtikaria: Int,
    @SerializedName("alergi_makanan")
    var alergiMakanan: Int,
    @SerializedName("interstitial_lung")
    var interstitialLung: Int,
)
