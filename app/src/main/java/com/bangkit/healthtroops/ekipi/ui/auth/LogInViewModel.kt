package com.bangkit.healthtroops.ekipi.ui.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authService: AuthService,
    private val sharedPref: SharedPreferences
) : ViewModel() {
    companion object {
        private const val TAG = "LogInViewModel"
    }

    private val remoteResponse = MutableLiveData<RemoteResponse>()

    fun logIn(email: String, password: String) {
        remoteResponse.postValue(RemoteResponse.loading())
        val call = authService.logIn(
            AccountResponse(null, email, password)
        )

        call.enqueue(object : Callback<QueryResponse<AccountResponse>> {
            override fun onResponse(
                call: Call<QueryResponse<AccountResponse>>,
                response: Response<QueryResponse<AccountResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.response.size == 1) {
                        val accountId = response.body()!!.response[0].id!!

                        sharedPref.edit {
                            putString(AuthActivity.AUTH_EMAIL, email)
                            putInt(AuthActivity.AUTH_ID, accountId)
                        }
                        remoteResponse.postValue(RemoteResponse.success())
                    } else {
                        remoteResponse.postValue(RemoteResponse.error("The email or password is incorrect"))
                    }
                } else {
                    Log.d(TAG, "onResponse: ${response.raw()}")
                    remoteResponse.postValue(RemoteResponse.error("not success: ${response.code()} = ${response.raw()}"))
                }
            }

            override fun onFailure(call: Call<QueryResponse<AccountResponse>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                remoteResponse.postValue(RemoteResponse.error("on Failure"))
            }
        })
    }

    fun getResponse(): LiveData<RemoteResponse> = remoteResponse
}
