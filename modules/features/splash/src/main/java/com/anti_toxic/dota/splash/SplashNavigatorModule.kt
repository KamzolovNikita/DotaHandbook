package com.anti_toxic.dota.splash

import com.anti_toxic.dota.core_api.di.FeatureMediator
import com.anti_toxic.dota.main.MainScreenMediator
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
object SplashNavigatorModule {

    @Provides
    fun provideMainScreenMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<FeatureMediator>>): MainScreenMediator {
        return map[MainScreenMediator::class.java]?.get() as MainScreenMediator
    }
}