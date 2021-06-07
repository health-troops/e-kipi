package com.bangkit.healthtroops.ekipi.ui.profileedit.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.network.ProfileService
import com.bangkit.healthtroops.ekipi.data.source.remote.response.InsertResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val authService: ProfileService,
    private val sharedPref: SharedPreferences
) :
    ViewModel() {
    val remoteResponse = MutableLiveData<RemoteResponse>()
    val userProfile = MutableLiveData<UserResponse>()
    val loading = MutableLiveData(false)

    fun setLoading(state: Boolean) = loading.postValue(state)

    fun getProfile() {
        val id = sharedPref.getInt(AuthActivity.AUTH_ID, 0)

        setLoading(true)
        authService.getUser(id).enqueue(object : Callback<QueryResponse<UserResponse>> {
            override fun onResponse(
                call: Call<QueryResponse<UserResponse>>,
                response: Response<QueryResponse<UserResponse>>
            ) {
                setLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.data.isNotEmpty()) userProfile.postValue(it.data[0])
                    }
                }
            }

            override fun onFailure(call: Call<QueryResponse<UserResponse>>, t: Throwable) {
                setLoading(false)
            }

        })
    }

    fun editProfile(user: UserResponse) {
        val id = sharedPref.getInt(AuthActivity.AUTH_ID, 0)
        user.accountId = id

        setLoading(true)
        authService.editUser(id, user).enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
                    user.accountId = response.body()!!.response.insertId
                    remoteResponse.postValue(RemoteResponse.success())
                } else {
                    remoteResponse.postValue(RemoteResponse.error("Failed Update Profile"))
                }
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                remoteResponse.postValue(RemoteResponse.error("onFailure"))
            }

        })
    }
}
