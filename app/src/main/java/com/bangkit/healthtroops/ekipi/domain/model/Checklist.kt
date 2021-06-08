package com.bangkit.healthtroops.ekipi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Checklist (
    val id: Int,
    val nama: String,
    val penanganan: String
) : Parcelable
