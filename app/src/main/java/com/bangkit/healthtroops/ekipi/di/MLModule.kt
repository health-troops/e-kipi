package com.bangkit.healthtroops.ekipi.di

import com.bangkit.healthtroops.ekipi.BuildConfig
import com.bangkit.healthtroops.ekipi.data.source.remote.network.MachineLearningSevice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MLModule {
    @Provides
    fun provideMLService(client: OkHttpClient) : MachineLearningSevice {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.ML_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MachineLearningSevice::class.java)
    }
}
