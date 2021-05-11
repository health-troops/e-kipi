package com.bangkit.healthtroops.ekipi.service.remote

import com.bangkit.healthtroops.ekipi.model.Account
import com.bangkit.healthtroops.ekipi.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    fun logIn(
        @Body body: Account,
    ): Call<LoginResponse>
}