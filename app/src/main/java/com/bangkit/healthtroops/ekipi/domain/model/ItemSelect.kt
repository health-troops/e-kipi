package com.bangkit.healthtroops.ekipi.domain.model

import android.view.View

data class ItemSelect(
    val name: String,
    val description: String,
    val onClickListener: View.OnClickListener
)
