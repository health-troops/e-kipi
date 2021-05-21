package com.bangkit.healthtroops.ekipi.di

import com.bangkit.healthtroops.ekipi.network.FormService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class FormModule {
    @Provides
    fun provideFormService(retrofit: Retrofit): FormService {
        return retrofit.create(FormService::class.java)
    }
}