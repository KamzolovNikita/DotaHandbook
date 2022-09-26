package com.anti_toxic.dota.core_impl.di

import android.content.Context
import com.anti_toxic.dota.core_api.di.ApplicationContext
import com.anti_toxic.dota.core_api.di.CoreProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules=[NetworkModule::class, SettingsModule::class]
)
interface CoreComponent: CoreProvider {

    companion object {

        fun create(context: Context): CoreProvider {
            return DaggerCoreComponent.factory().create(context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance @ApplicationContext context: Context): CoreComponent
    }
}