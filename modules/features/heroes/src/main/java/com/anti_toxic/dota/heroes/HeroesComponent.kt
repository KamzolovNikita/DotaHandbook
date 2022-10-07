package com.anti_toxic.dota.heroes

import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.heroes.list.HeroesListFragment
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import dagger.Component

@Component(
    dependencies = [
        CoreProvider::class,
        OpenDotaApiServiceProvider::class,
        DatabaseProvider::class
    ]
)
interface HeroesComponent {

    @Component.Factory
    interface Factory {
        fun create(
            containerActivityProvider: CoreProvider,
            openDotaApiServiceProvider: OpenDotaApiServiceProvider,
            databaseProvider: DatabaseProvider
        ): HeroesComponent
    }

    fun inject(heroesListFragment: HeroesListFragment)
}