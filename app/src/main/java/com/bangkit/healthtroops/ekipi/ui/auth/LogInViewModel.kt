package com.bangkit.healthtroops.ekipi.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {
    companion object {
        private const val TAG = "LogInViewModel"
    }

    private val remoteResponse = MutableLiveData<RemoteResponse>()

    fun logIn(email: String, password: String) {
        remoteResponse.postValue(RemoteResponse.loading())
        val call = authService.logIn(
            Account(null, email, password)
        )

        call.enqueue(object : Callback<QueryResponse<Account>> {
            override fun onResponse(
                call: Call<QueryResponse<Account>>,
                response: Response<QueryResponse<Account>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.response.size == 1) {
                        remoteResponse.postValue(RemoteResponse.success())
                    } else {
                        remoteResponse.postValue(RemoteResponse.error("The email or password is incorrect"))
                    }
                } else {
                    Log.d(TAG, "onResponse: ${response.raw()}")
                    remoteResponse.postValue(RemoteResponse.error("not success: ${response.code()} = ${response.raw()}"))
                }
            }

            override fun onFailure(call: Call<QueryResponse<Account>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                remoteResponse.postValue(RemoteResponse.error("on Failure"))
            }
        })
    }

    fun getResponse(): LiveData<RemoteResponse> = remoteResponse
}
