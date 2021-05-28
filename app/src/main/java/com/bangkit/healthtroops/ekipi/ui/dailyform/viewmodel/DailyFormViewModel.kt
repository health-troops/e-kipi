package com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.source.remote.response.ChecklistResponse
import com.bangkit.healthtroops.ekipi.domain.model.FormChecklist
import com.bangkit.healthtroops.ekipi.data.source.remote.network.FormService
import com.bangkit.healthtroops.ekipi.data.source.remote.response.QueryResponse
import com.bangkit.healthtroops.ekipi.utils.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DailyFormViewModel @Inject constructor(private val formService: FormService) : ViewModel() {

    val checklists = MutableLiveData<List<FormChecklist>>()
    val loading = MutableLiveData(false)

    fun getAllChecklist() {
        loading.postValue(true)
        formService.getFormChecklist().enqueue(object : Callback<QueryResponse<ChecklistResponse>> {
            override fun onResponse(
                call: Call<QueryResponse<ChecklistResponse>>,
                response: Response<QueryResponse<ChecklistResponse>>
            ) {
                if(response.isSuccessful) {
                    val resBody = response.body()
                    if (resBody != null) {
                        checklists.postValue(DataMapper.mapResponseToDomain(resBody.data))
                    }
                } else {
                    Log.d("Checklists", response.message())
                }
                loading.postValue(false)
            }

            override fun onFailure(call: Call<QueryResponse<ChecklistResponse>>, t: Throwable) {
                Log.d("Checcklits", t.message.toString())
                loading.postValue(false)
            }

        })
    }

    fun sendData(checks: List<Int>, description: String?) {
        Log.d("SEND", checks.toString())
        Log.d("SEND", description ?: "")
    }
}
