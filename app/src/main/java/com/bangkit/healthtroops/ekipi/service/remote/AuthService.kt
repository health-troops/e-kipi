package com.bangkit.healthtroops.ekipi.service.remote

import com.bangkit.healthtroops.ekipi.model.Account
import com.bangkit.healthtroops.ekipi.model.InsertResponse
import com.bangkit.healthtroops.ekipi.model.QueryResponse
import com.bangkit.healthtroops.ekipi.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    companion object {
//        const val BASE_URL = "https://causal-tracker-312605.uc.r.appspot.com/api/"
        const val BASE_URL = "http://192.168.1.16:4000/api/"
    }

    @POST("login")
    fun logIn(@Body body: Account): Call<QueryResponse<Account>>

    @POST("users")
    fun registerUser(@Body body: User): Call<InsertResponse>

    @POST("register")
    fun registerAccount(@Body body: Account): Call<InsertResponse>
}