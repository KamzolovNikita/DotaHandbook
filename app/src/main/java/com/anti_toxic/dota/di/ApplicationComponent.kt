package com.anti_toxic.dota.di

import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceModule
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseModule
import com.anti_toxic.dota.databases.di.DatabaseProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreProvider::class],
    modules = [OpenDotaApiServiceModule::class, DatabaseModule::class]
)
interface ApplicationComponent: CoreProvider, OpenDotaApiServiceProvider, DatabaseProvider {

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): ApplicationComponent
    }
}

