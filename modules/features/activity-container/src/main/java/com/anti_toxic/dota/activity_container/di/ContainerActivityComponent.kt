package com.anti_toxic.dota.activity_container.di

import android.app.Activity
import androidx.navigation.NavController
import com.anti_toxic.dota.core_api.di.ActivityScope
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.core_api.di.ContainerActivityProvider
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [MainContainerNavigatorModule::class]
)
interface ContainerActivityComponent : ContainerActivityProvider {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity,
            @BindsInstance navController: NavController
        ): ContainerActivityComponent
    }
}