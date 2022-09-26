package com.anti_toxic.dota.open_dota_api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object OpenDotaApiServiceModule {

    @Provides
    @Singleton
    fun provideOpenDotaApiService(json: Json, okHttpClient: OkHttpClient): OpenDotaApiService {
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl(OpenDotaApiService.BASE_URL)
            .addConverterFactory(json.asConverterFactory(mediaType))
            .client(okHttpClient)
            .build()
            .create(OpenDotaApiService::class.java)
    }
}