package com.anti_toxic.dota.core_impl.di

import com.anti_toxic.dota.core_api.ActualAppDispatchers
import com.anti_toxic.dota.core_api.AppDispatchers
import com.anti_toxic.dota.core_api.PreferencesProvider
import com.anti_toxic.dota.core_impl.PreferencesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
interface SettingsModule {

    @Binds
    @Singleton
    fun bindPreferencesProvider(preferencesProvider: PreferencesProviderImpl): PreferencesProvider

    companion object {
        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                coerceInputValues = true
            }
        }
    }

    @Binds
    fun bindAppDispatchers(appDispatchers: ActualAppDispatchers): AppDispatchers
}