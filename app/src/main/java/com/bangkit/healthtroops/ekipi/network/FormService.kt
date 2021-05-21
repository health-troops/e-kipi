package com.bangkit.healthtroops.ekipi.network

import com.bangkit.healthtroops.ekipi.data.ComorbidSymptom
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import retrofit2.Call
import retrofit2.http.GET

interface FormService {
    @GET("api/checklist")
    fun getFormChecklist() : Call<QueryResponse<ComorbidSymptom>>
}