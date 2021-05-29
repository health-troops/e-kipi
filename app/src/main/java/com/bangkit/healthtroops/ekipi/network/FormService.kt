package com.bangkit.healthtroops.ekipi.network

import com.bangkit.healthtroops.ekipi.data.DailyForm
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FormService {
    @GET("api/checklists")
    fun getFormChecklist() : Call<QueryResponse<ChecklistResponse>>

    @POST("api/formkipi")
    fun postDailyForm(@Body body: DailyForm) : Call<InsertResponse>

}