package com.bangkit.healthtroops.ekipi.network

import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import retrofit2.Call
import retrofit2.http.GET

interface FormService {
    @GET("api/checklists")
    fun getFormChecklist() : Call<QueryResponse<ChecklistResponse>>
}