package com.anti_toxic.dota

import android.app.Application
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_factory.BuildConfig
import com.anti_toxic.dota.core_factory.CoreFactory
import com.anti_toxic.dota.di.ApplicationComponent
import com.anti_toxic.dota.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class App: Application(), ComponentProvider {

    private var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        createComponent()
        plantTimber()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> provide(klass: Class<T>): T {
        val component = this.component ?: createComponent()
        if (klass.isAssignableFrom(component::class.java)) {
            return component as T
        } else {
            throw IllegalArgumentException("$klass cannot be provided by application component")
        }
    }

    private fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(
            CoreFactory.createCoreProvider(applicationContext)
        ).also {
            component = it
        }
    }
}