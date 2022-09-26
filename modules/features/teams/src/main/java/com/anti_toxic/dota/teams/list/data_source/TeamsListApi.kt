package com.anti_toxic.dota.teams.list.data_source

import com.anti_toxic.dota.open_dota_api.OpenDotaApiService
import javax.inject.Inject

class TeamsListApi @Inject constructor(
    private val openDotaApiService: OpenDotaApiService
) {
    suspend fun getTeams() = openDotaApiService.getTeams()
}