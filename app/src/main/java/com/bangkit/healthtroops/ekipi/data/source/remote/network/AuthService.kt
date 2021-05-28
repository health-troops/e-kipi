package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.InsertResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun logIn(@Body body: AccountResponse): QueryResponse<AccountResponse>

    @POST("api/users")
    suspend fun registerUser(@Body body: UserResponse): InsertResponse

    @POST("api/register")
    suspend fun registerAccount(@Body body: AccountResponse): InsertResponse
}
