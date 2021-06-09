package com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.data.DailyForm
import com.bangkit.healthtroops.ekipi.data.MLRequest
import com.bangkit.healthtroops.ekipi.data.source.remote.network.FormService
import com.bangkit.healthtroops.ekipi.data.source.remote.network.MachineLearningSevice
import com.bangkit.healthtroops.ekipi.data.source.remote.response.*
import com.bangkit.healthtroops.ekipi.domain.model.Checklist
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import com.bangkit.healthtroops.ekipi.utils.DataMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DailyFormViewModel @Inject constructor(
    private val formService: FormService,
    private val machineLearningSevice: MachineLearningSevice,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val checklists = MutableLiveData<List<Checklist>>()
    val loading = MutableLiveData(false)
    val success = MutableLiveData(false)
    val recommendation = MutableLiveData<MLResponse>()
    val error = MutableLiveData<String>()
    val moodPrediction = MutableLiveData<String>()

    fun getAllChecklist() {
        loading.postValue(true)
        formService.getFormChecklist().enqueue(object : Callback<QueryResponse<ChecklistResponse>> {
            override fun onResponse(
                call: Call<QueryResponse<ChecklistResponse>>,
                response: Response<QueryResponse<ChecklistResponse>>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    if (resBody != null) {
                        checklists.postValue(DataMapper.mapResponseToDomain(resBody.data))
                    }
                } else {
                    Log.d("Checklists", response.message())
                    error.postValue("Failed get Checklist")
                }
                loading.postValue(false)
            }

            override fun onFailure(call: Call<QueryResponse<ChecklistResponse>>, t: Throwable) {
                Log.d("Checcklits", t.message.toString())
                error.postValue("Failed get Checklist")
                loading.postValue(false)
            }

        })
    }

    fun sendData(checks: List<Int>, description: String) {
        Log.d("SEND", checks.toString())
        Log.d("SEND", description)
        loading.postValue(true)
        val idAccount = sharedPreferences.getInt(AuthActivity.AUTH_ID, 0)
        val mlRequestBody = MLRequest(
            symptoms = checks,
            text = description
        )
        machineLearningSevice.postMachineLearning(mlRequestBody).enqueue(object :
            Callback<MLResponse> {
            override fun onResponse(call: Call<MLResponse>, response: Response<MLResponse>) {
                val resBody = response.body()
                if (response.isSuccessful && response.body() != null) {
                    recommendation.postValue(resBody!!)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = Date()
                    postBackend(
                        DailyForm(
                            predictionClass0 = resBody.prediction[0],
                            predictionClass1 = resBody.prediction[1],
                            predictionClass2 = resBody.prediction[2],
                            idAccount = idAccount,
                            tanggal = dateFormat.format(date),
                            lainnya = getDescriptionWithMood(description),
                            diagnosis = formatTreatmentsToString(resBody.treatment),
                            recommendation = resBody.recommendation.rekomendasi,
                            checklist = checks
                        )
                    )
                } else {
                    Log.d("ML", response.message())
                    error.postValue("Prediction Error")
                    loading.postValue(false)
                }
            }

            override fun onFailure(call: Call<MLResponse>, t: Throwable) {
                Log.d("ML", t.message.toString())
                error.postValue("Prediction Error")
                loading.postValue(false)
            }

        })
    }

    private fun postBackend(dailyForm: DailyForm) {
        formService.postDailyForm(dailyForm).enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: Response<InsertResponse>
            ) {
                val resBody = response.body()
                if (response.isSuccessful) {
                    success.postValue(true)
                } else {
                    Log.d("Backend", resBody?.error?.sqlMessage ?: response.message())
                    error.postValue("Failed to send data")
                }
                loading.postValue(false)
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                Log.d("Backend", t.message.toString())
                error.postValue("Failed to send data")
                loading.postValue(false)
            }
        })
    }

    private fun formatTreatmentsToString(treatments: List<TreatmentItem>): String {
        val listValues = treatments.map { it.gejala + "\n" + it.penanganan }
        return listValues.joinToString("\n\n")
    }

    private fun getDescriptionWithMood(description: String): String {
        val mood = moodPrediction.value
        return if (mood != null) {
            "$mood - $description"
        } else {
            description
        }
    }
}
