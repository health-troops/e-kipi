package com.bangkit.healthtroops.ekipi.data.source.remote

import com.bangkit.healthtroops.ekipi.data.source.remote.network.ApiResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.network.AuthService
import com.bangkit.healthtroops.ekipi.data.source.remote.network.FormService
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
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
    private val formService: FormService,
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
                        authService.deleteUserById(accountId)
                        emit(ApiResponse.Error(registerUser.error.sqlMessage))
                    }
                } else {
                    emit(ApiResponse.Error(registerAccount.error.sqlMessage))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFormKipiDaily(accountId: Int): Flow<ApiResponse<List<FormKipiDailyResponse>>> {
        return flow {
            try {
                val response = formService.getFormKipiDaily(accountId)
                if (response.error == null) {
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Error(response.error))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSymptomNamesByFormId(formId: Int) =
        flow<ApiResponse<List<String>>> {
            try {
                val response = formService.getSymptomNamesByFormId(formId)
                if (response.error == null) {
                    val list = mutableListOf<String>()
                    response.data.forEach { map -> list.add(map["nama_gejala"] ?: "") }
                    emit(ApiResponse.Success(list))
                } else {
                    emit(ApiResponse.Error(response.error))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
}
