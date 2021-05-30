package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FormService {
    @GET("api/checklists")
    fun getFormChecklist(): Call<QueryResponse<ChecklistResponse>>

    @GET("api/formkipidaily")
    suspend fun getFormKipiDaily(
        @Query("id_account") idAccount: Int
    ): QueryResponse<FormKipiDailyResponse>
}
