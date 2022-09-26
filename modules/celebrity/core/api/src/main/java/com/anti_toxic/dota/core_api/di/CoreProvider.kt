package com.anti_toxic.dota.core_api.di

import android.content.Context
import com.anti_toxic.dota.core_api.AppDispatchers
import com.anti_toxic.dota.core_api.PreferencesProvider
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

interface CoreProvider {

    @ApplicationContext
    fun provideContext(): Context

    fun providePreferencesProvider(): PreferencesProvider

    val json: Json

    fun provideOkHttpClient(): OkHttpClient

    fun provideAppDispatchers(): AppDispatchers
}