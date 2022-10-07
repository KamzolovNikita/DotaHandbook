package com.anti_toxic.dota.items.list.data_source

import com.anti_toxic.dota.open_dota_api.OpenDotaApiService
import javax.inject.Inject

class ItemsListApi @Inject constructor(
    private val openDotaApiService: OpenDotaApiService
) {

    suspend fun getItems() = openDotaApiService.getItems()
}