package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import retrofit2.Call
import retrofit2.http.GET

interface FormService {
    @GET("api/checklists")
    fun getFormChecklist(): Call<QueryResponse<ChecklistResponse>>
}
