package com.bangkit.healthtroops.ekipi.di

import com.bangkit.healthtroops.ekipi.BuildConfig.BASE_URL
import com.bangkit.healthtroops.ekipi.network.AuthService
import com.bangkit.healthtroops.ekipi.network.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }
}
