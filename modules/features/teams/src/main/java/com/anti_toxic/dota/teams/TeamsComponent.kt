package com.anti_toxic.dota.teams

import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.teams.list.TeamsListFragment
import dagger.Component

@Component(
    dependencies = [
        CoreProvider::class,
        OpenDotaApiServiceProvider::class,
        DatabaseProvider::class
    ]
)
interface TeamsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            containerActivityProvider: CoreProvider,
            openDotaApiServiceProvider: OpenDotaApiServiceProvider,
            databaseProvider: DatabaseProvider
        ): TeamsComponent
    }

    fun inject(teamsListFragment: TeamsListFragment)
}