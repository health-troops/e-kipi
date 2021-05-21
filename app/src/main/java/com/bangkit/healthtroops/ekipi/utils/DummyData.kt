package com.bangkit.healthtroops.ekipi.utils

import android.content.Context
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.ComorbidSymptom

object DummyData {
    fun generateComorbidSymptoms(context: Context): List<ComorbidSymptom> {
        val symptoms = context.resources.getStringArray(R.array.comorbid_symptoms)
        return symptoms.map { s -> ComorbidSymptom(s) }.toList()
    }
}
