package com.bangkit.healthtroops.ekipi.network

import com.bangkit.healthtroops.ekipi.data.Comorbid
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {
    @POST("api/komorbid")
    fun saveComorbid(@Body body: Comorbid): Call<InsertResponse>
}
