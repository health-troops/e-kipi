package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.QueryResponse
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ComorbidDataResponse
import com.bangkit.healthtroops.ekipi.domain.model.ComorbidData
import com.bangkit.healthtroops.ekipi.network.ProfileService
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import com.bangkit.healthtroops.ekipi.utils.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpComorbidViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val sharedPref: SharedPreferences
) :
    ViewModel() {

    private val comorbidResponse = MutableLiveData<ComorbidData>()
    private val remoteResponse = MutableLiveData<RemoteResponse>()

    fun getComorbid() {
        val accountId = sharedPref.getInt(AuthActivity.AUTH_ID, -1)
        val call = profileService.getComorbid(accountId)

        call.enqueue(object : Callback<QueryResponse<ComorbidDataResponse>> {
            override fun onResponse(
                call: Call<QueryResponse<ComorbidDataResponse>>,
                response: Response<QueryResponse<ComorbidDataResponse>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "onResponse: $body")
                    if (body != null && body.response.size == 1) {
                        comorbidResponse.postValue(DataMapper.mapResponseToDomain(body.response[0]))
                    }
                } else {
                    remoteResponse.postValue(RemoteResponse.error("Failed register user"))
                }
            }

            override fun onFailure(call: Call<QueryResponse<ComorbidDataResponse>>, t: Throwable) {
                remoteResponse.postValue(RemoteResponse.error("onFailure"))
            }
        })
    }

    fun saveComorbid(comorbidData: ComorbidData) {
        val accountId = comorbidData.idAccount
        val call: Call<InsertResponse> = if (comorbidResponse.value == null) {
            profileService.saveComorbid(DataMapper.mapDomainToResponse(comorbidData))
        } else {
            profileService.putComorbid(accountId, DataMapper.mapDomainToResponse(comorbidData))
        }

        call.enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
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

    fun getComorbidResponse(): LiveData<ComorbidData> = comorbidResponse
    fun getRemoteResponse(): LiveData<RemoteResponse> = remoteResponse

    companion object {
        private const val TAG = "SignUpComorbidViewModel"
    }
}
