package com.bangkit.healthtroops.ekipi.data.source.remote

import com.bangkit.healthtroops.ekipi.data.source.remote.network.ApiResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.network.AuthService
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun logIn(accountResponse: AccountResponse): Flow<ApiResponse<AccountResponse>> {
        return flow {
            try {
                val response = authService.logIn(accountResponse)
                val accounts = response.data
                if (accounts.size == 1) {
                    emit(ApiResponse.Success(accounts[0]))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun register(
        user: UserResponse,
        account: AccountResponse
    ): Flow<ApiResponse<Int>> {
        return flow {
            try {
                val registerAccount = authService.registerAccount(account)
                if (registerAccount.error == null) {
                    val accountId = registerAccount.response.insertId
                    user.accountId = accountId

                    val registerUser = authService.registerUser(user)
                    if (registerUser.error == null) {
                        emit(ApiResponse.Success(accountId))
                    } else {
                        emit(ApiResponse.Error(registerUser.error))
                    }
                } else {
                    emit(ApiResponse.Error(registerAccount.error))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
