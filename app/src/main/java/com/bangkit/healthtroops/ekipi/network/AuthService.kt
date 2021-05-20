package com.bangkit.healthtroops.ekipi.network

import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.User
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @POST("api/login")
    fun logIn(@Body body: Account): Call<QueryResponse<Account>>

    @POST("api/users")
    fun registerUser(@Body body: User): Call<InsertResponse>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: Int): Call<QueryResponse<User>>

    @PUT("api/users/{id}")
    fun editUser(@Path("id") id: Int, @Body body: User): Call<InsertResponse>

    @POST("api/register")
    fun registerAccount(@Body body: Account): Call<InsertResponse>

}