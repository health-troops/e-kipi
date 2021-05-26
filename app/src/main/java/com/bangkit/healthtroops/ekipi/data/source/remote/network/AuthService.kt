package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.InsertResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun logIn(@Body body: AccountResponse): QueryResponse<AccountResponse>

    @POST("api/users")
    fun registerUser(@Body body: UserResponse): Call<InsertResponse>

    @POST("api/register")
    fun registerAccount(@Body body: AccountResponse): Call<InsertResponse>
}
