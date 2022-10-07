package com.anti_toxic.dota.items.list.data_source

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.network.request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemsListRepository @Inject constructor(
    private val itemsListCache: ItemsListCache,
    private val itemsListApi: ItemsListApi
) {

    suspend fun getItems(): Flow<Resource<List<Item>>> {
        return request(
            fetchCache = {
                itemsListCache.getItems()
                    .takeIf { it.isNotEmpty() }
                    ?.sortedBy { it.itemId }
            },
            fetchRemote = {
                itemsListApi.getItems()
                    .values.toList()
                    //hack to avoid recipes items
                    .filter { it.lore.isNotEmpty() }
                    .sortedBy { it.itemId }
            },
            updateCache = { value -> itemsListCache.updateCache(value) },
            mapper = ItemsListMapper,
            forceUpdate = false
        )
    }
}