package com.bangkit.healthtroops.ekipi.data

import com.google.gson.annotations.SerializedName

data class ComorbidResponse(
    @SerializedName("id_account")
    var idAccount: Int,
    var hipertensi: Boolean,
    @SerializedName("diabetes_melitus")
    var diabetesMelitus: Boolean,
    @SerializedName("gagal_jantung")
    var gagalJantung: Boolean,
    @SerializedName("jantung_koroner")
    var jantungKoroner: Boolean,
    @SerializedName("paru_obstruktif_knonis")
    var paruObstruktifKronis: Boolean,
    var asma: Boolean,
    var hati: Boolean,
    var tbc: Boolean,
    var autoimun: Boolean,
    var kanker: Boolean,
    var hiv: Boolean,
    @SerializedName("alergi_obat")
    var alergiObat: Boolean,
    @SerializedName("kelainan_darah")
    var kelainanDarah: Boolean,
    var hipertiroid: Boolean,
    var ginjal: Boolean,
    @SerializedName("dermatitis_atopi")
    var dermatitisAtopi: Boolean,
    @SerializedName("reaksi_anafilaksis")
    var reaksiAnafilaksis: Boolean,
    var urtikaria: Boolean,
    @SerializedName("alergi_makanan")
    var alergiMakanan: Boolean,
    @SerializedName("interstitial_lung")
    var interstitialLung: Boolean,
)
