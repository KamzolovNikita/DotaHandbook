package com.anti_toxic.dota.items

import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.items.info.ItemInfoFragment
import com.anti_toxic.dota.items.list.ItemsListFragment
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import dagger.Component

@Component(
    dependencies = [
        CoreProvider::class,
        OpenDotaApiServiceProvider::class,
        DatabaseProvider::class
    ]
)
interface ItemsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            containerActivityProvider: CoreProvider,
            openDotaApiServiceProvider: OpenDotaApiServiceProvider,
            databaseProvider: DatabaseProvider
        ): ItemsComponent
    }

    fun inject(itemsListFragment: ItemsListFragment)

    fun inject(itemInfoFragment: ItemInfoFragment)
}