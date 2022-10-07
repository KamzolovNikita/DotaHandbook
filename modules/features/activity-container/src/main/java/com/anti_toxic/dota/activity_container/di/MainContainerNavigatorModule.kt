package com.anti_toxic.dota.activity_container.di

import androidx.navigation.NavController
import com.anti_toxic.dota.activity_container.MainContainerNavigator
import com.anti_toxic.dota.core_api.di.ActivityScope
import com.anti_toxic.dota.core_api.di.FeatureMediator
import com.anti_toxic.dota.main.MainScreenMediator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface MainContainerNavigatorModule {

    companion object {
        @Provides
        @ActivityScope
        fun provideNavigator(navController: NavController): MainContainerNavigator {
            return MainContainerNavigator(navController)
        }
    }

    @Binds
    @IntoMap
    @ClassKey(MainScreenMediator::class)
    fun putMainScreenMediator(mainContainerNavigator: MainContainerNavigator): FeatureMediator
}