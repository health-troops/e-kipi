package com.bangkit.healthtroops.ekipi.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormChecklist (
    val id: Int,
    val nama: String,
    val penanganan: String
) : Parcelable
