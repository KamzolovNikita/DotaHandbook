package com.anti_toxic.dota.heroes.list.data_source

import com.anti_toxic.dota.open_dota_api.OpenDotaApiService
import javax.inject.Inject

class HeroesListApi @Inject constructor(
    private val openDotaApiService: OpenDotaApiService
) {
    suspend fun getHeroes() = openDotaApiService.getHeroes()
}