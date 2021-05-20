package com.bangkit.healthtroops.ekipi.ui.profileedit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.User
import com.bangkit.healthtroops.ekipi.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {
    val remoteResponse = MutableLiveData<RemoteResponse>()
    val userProfile = MutableLiveData<User>()
    val loading = MutableLiveData(false)

    fun setLoading(state: Boolean) = loading.postValue(state)

    fun getProfile (id: Int) {
        setLoading(true)
        authService.getUser(id).enqueue(object : Callback<QueryResponse<User>> {
            override fun onResponse(
                call: Call<QueryResponse<User>>,
                response: Response<QueryResponse<User>>
            ) {
                setLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.response.isNotEmpty()) userProfile.postValue(it.response[0])
                    }
                }
            }

            override fun onFailure(call: Call<QueryResponse<User>>, t: Throwable) {
                setLoading(false)
            }

        })
    }

    fun editProfile (id: Int, user: User) {
        setLoading(true)
        authService.editUser(id, user).enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
                    user.accountId = response.body()!!.response.insertId
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