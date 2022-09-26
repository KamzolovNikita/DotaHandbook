package com.anti_toxic.dota.splash

import com.anti_toxic.dota.core_api.di.ContainerActivityProvider
import dagger.Component

@Component(
    modules = [SplashNavigatorModule::class],
    dependencies = [ContainerActivityProvider::class]
)
interface SplashComponent {

    @Component.Factory
    interface Factory {
        fun create(
            activityProvisions: ContainerActivityProvider
        ): SplashComponent
    }

    fun inject(splashFragment: SplashFragment)
}