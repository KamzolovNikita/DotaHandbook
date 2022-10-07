package com.anti_toxic.dota.items.list.data_source

import com.anti_toxic.dota.databases.items.ItemEntity
import com.anti_toxic.dota.databases.items.ItemsDatabase
import javax.inject.Inject

class ItemsListCache @Inject constructor(
    itemsDatabase: ItemsDatabase
) {
    private val dao = itemsDatabase.itemsDao()

    suspend fun getItems(): List<ItemEntity> {
        return dao.getAllItems()
    }

    suspend fun updateCache(items: List<ItemEntity>) {
        return dao.updateItemsData(items)
    }
}