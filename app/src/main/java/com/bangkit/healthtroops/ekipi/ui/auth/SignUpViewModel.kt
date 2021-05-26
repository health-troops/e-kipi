package com.bangkit.healthtroops.ekipi.ui.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.User
import com.bangkit.healthtroops.ekipi.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService,
    private val sharedPref: SharedPreferences
) : ViewModel() {
    private val remoteResponse = MutableLiveData<RemoteResponse>()

    fun getResponse(): LiveData<RemoteResponse> = remoteResponse

    fun register(user: User, account: Account) {
        remoteResponse.postValue(RemoteResponse.loading())

        val call = authService.registerAccount(account)
        call.enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
                    user.accountId = response.body()!!.response.insertId
                    registerUser(user, account.email)
                } else {
                    remoteResponse.postValue(RemoteResponse.error("Failed register account"))
                }
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                remoteResponse.postValue(RemoteResponse.error("on Failure"))
            }
        })
    }

    private fun registerUser(user: User, email: String) {
        val call = authService.registerUser(user)

        call.enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
                    sharedPref.edit {
                        putString(AuthActivity.AUTH_EMAIL, email)
                        putInt(AuthActivity.AUTH_ID, user.accountId)
                    }
                    remoteResponse.postValue(RemoteResponse.success())
                } else {
                    remoteResponse.postValue(RemoteResponse.error("Failed register user"))
                }
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                remoteResponse.postValue(RemoteResponse.error("onFailure"))
            }
        })
    }
}
