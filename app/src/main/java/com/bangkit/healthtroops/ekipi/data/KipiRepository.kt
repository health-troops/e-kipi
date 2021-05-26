package com.bangkit.healthtroops.ekipi.data

import com.bangkit.healthtroops.ekipi.data.source.remote.RemoteDataSource
import com.bangkit.healthtroops.ekipi.data.source.remote.network.ApiResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KipiRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun logIn(accountResponse: AccountResponse) =
        flow<Resource<AccountResponse>> {
            emit(Resource.Loading())

            when (val account = remoteDataSource.logIn(accountResponse).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(account.data))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("wrong username or password"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error("error"))
                }
            }
        }
}
