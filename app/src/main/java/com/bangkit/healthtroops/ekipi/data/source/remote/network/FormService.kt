package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.DailyForm
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.InsertResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FormService {
    @GET("api/checklists")
    fun getFormChecklist(): Call<QueryResponse<ChecklistResponse>>

    @POST("api/formkipi")
    fun postDailyForm(@Body body: DailyForm) : Call<InsertResponse>

    @GET("api/formkipidaily")
    suspend fun getFormKipiDaily(
        @Query("id_account") idAccount: Int
    ): QueryResponse<FormKipiDailyResponse>
}
