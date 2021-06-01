package com.bangkit.healthtroops.ekipi.data.source.remote.network

import com.bangkit.healthtroops.ekipi.data.MLRequest
import com.bangkit.healthtroops.ekipi.data.source.remote.response.MLResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MachineLearningSevice {
    @POST("/")
    fun postMachineLearning(@Body body: MLRequest) : Call<MLResponse>
}
