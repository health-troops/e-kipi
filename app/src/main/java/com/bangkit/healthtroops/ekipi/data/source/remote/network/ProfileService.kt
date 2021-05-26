package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.source.remote.response.InsertResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ComorbidDataResponse
import retrofit2.Call
import retrofit2.http.*

interface ProfileService {
    @POST("api/komorbid")
    fun saveComorbid(@Body body: ComorbidDataResponse): Call<InsertResponse>

    @GET("api/komorbid/{id}")
    fun getComorbid(@Path("id") accountId: Int): Call<QueryResponse<ComorbidDataResponse>>

    @PUT("api/komorbid/{id}")
    fun putComorbid(
        @Path("id") accountId: Int,
        @Body body: ComorbidDataResponse
    ): Call<InsertResponse>
}
