package com.bangkit.healthtroops.ekipi.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bangkit.healthtroops.ekipi.data.source.remote.RemoteDataSource
import com.bangkit.healthtroops.ekipi.data.source.remote.network.ApiResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KipiRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sharedPref: SharedPreferences,
) {
    fun logIn(accountResponse: AccountResponse) =
        flow<Resource<AccountResponse>> {
            emit(Resource.Loading())

            when (val account = remoteDataSource.logIn(accountResponse).first()) {
                is ApiResponse.Success -> {
                    val email = account.data.email
                    val accountId = account.data.id

                    if (accountId != null) {
                        sharedPref.edit {
                            putString(AuthActivity.AUTH_EMAIL, email)
                            putInt(AuthActivity.AUTH_ID, accountId)
                        }
                        emit(Resource.Success(account.data))
                    } else {
                        emit(Resource.Error("no user id"))
                    }
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("wrong username or password"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(account.errorMessage))
                }
            }
        }

    fun register(user: UserResponse, account: AccountResponse) =
        flow<Resource<Boolean>> {
            emit(Resource.Loading())

            when (val result = remoteDataSource.register(user, account).first()) {
                is ApiResponse.Success -> {
                    val accountId = result.data
                    sharedPref.edit {
                        putString(AuthActivity.AUTH_EMAIL, account.email)
                        putInt(AuthActivity.AUTH_ID, accountId)
                    }
                    emit(Resource.Success(true))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Error("something went wrong"))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(result.errorMessage))
                }
            }
        }

    fun getFormKipiDaily() =
        flow<Resource<List<FormKipiDailyResponse>>> {
            val accountId = sharedPref.getInt(AuthActivity.AUTH_ID, 0)
            emit(Resource.Loading())

            when (val result = remoteDataSource.getFormKipiDaily(accountId).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(result.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(result.errorMessage))
                }
                else -> {
                    emit(Resource.Error("something went wrong"))
                }
            }
        }
}
