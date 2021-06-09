package com.bangkit.healthtroops.ekipi.utils

import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ComorbidDataResponse
import com.bangkit.healthtroops.ekipi.domain.model.ComorbidData
import com.bangkit.healthtroops.ekipi.domain.model.Checklist

object DataMapper {
    fun mapResponseToDomain(input: ComorbidDataResponse) = ComorbidData(
        idAccount = input.idAccount,
        hipertensi = input.hipertensi.toBoolean(),
        diabetesMelitus = input.diabetesMelitus.toBoolean(),
        gagalJantung = input.gagalJantung.toBoolean(),
        jantungKoroner = input.jantungKoroner.toBoolean(),
        paruObstruktifKronis = input.paruObstruktifKronis.toBoolean(),
        asma = input.asma.toBoolean(),
        hati = input.hati.toBoolean(),
        tbc = input.tbc.toBoolean(),
        autoimun = input.autoimun.toBoolean(),
        kanker = input.kanker.toBoolean(),
        hiv = input.hiv.toBoolean(),
        alergiObat = input.alergiObat.toBoolean(),
        kelainanDarah = input.kelainanDarah.toBoolean(),
        hipertiroid = input.hipertiroid.toBoolean(),
        ginjal = input.ginjal.toBoolean(),
        dermatitisAtopi = input.dermatitisAtopi.toBoolean(),
        reaksiAnafilaksis = input.reaksiAnafilaksis.toBoolean(),
        urtikaria = input.urtikaria.toBoolean(),
        alergiMakanan = input.alergiMakanan.toBoolean(),
        interstitialLung = input.interstitialLung.toBoolean()
    )

    fun mapDomainToResponse(input: ComorbidData) = ComorbidDataResponse(
        idAccount = input.idAccount,
        hipertensi = input.hipertensi.toInt(),
        diabetesMelitus = input.diabetesMelitus.toInt(),
        gagalJantung = input.gagalJantung.toInt(),
        jantungKoroner = input.jantungKoroner.toInt(),
        paruObstruktifKronis = input.paruObstruktifKronis.toInt(),
        asma = input.asma.toInt(),
        hati = input.hati.toInt(),
        tbc = input.tbc.toInt(),
        autoimun = input.autoimun.toInt(),
        kanker = input.kanker.toInt(),
        hiv = input.hiv.toInt(),
        alergiObat = input.alergiObat.toInt(),
        kelainanDarah = input.kelainanDarah.toInt(),
        hipertiroid = input.hipertiroid.toInt(),
        ginjal = input.ginjal.toInt(),
        dermatitisAtopi = input.dermatitisAtopi.toInt(),
        reaksiAnafilaksis = input.reaksiAnafilaksis.toInt(),
        urtikaria = input.urtikaria.toInt(),
        alergiMakanan = input.alergiMakanan.toInt(),
        interstitialLung = input.interstitialLung.toInt()
    )

    fun mapResponseToDomain(input: List<ChecklistResponse>) = input.map {
        Checklist(
            it.id,
            it.nama,
            it.penanganan
        )
    }

    private fun Int.toBoolean() = this == 1
    private fun Boolean.toInt() = if (this) 1 else 0
}
