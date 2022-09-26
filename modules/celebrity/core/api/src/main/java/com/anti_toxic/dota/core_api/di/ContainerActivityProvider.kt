package com.anti_toxic.dota.core_api.di

import android.app.Activity
import javax.inject.Provider

interface ContainerActivityProvider {

    fun provideActivity(): Activity

    fun provideMediators(): Map<Class<*>, @JvmSuppressWildcards Provider<FeatureMediator>>
}