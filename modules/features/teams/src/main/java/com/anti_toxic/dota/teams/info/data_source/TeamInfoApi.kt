package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.open_dota_api.OpenDotaApiService
import javax.inject.Inject

class TeamInfoApi @Inject constructor(
    private val openDotaApiService: OpenDotaApiService
) {
    suspend fun getPlayers(teamId: Int) = openDotaApiService.getPlayers(teamId)

    suspend fun getMatches(teamId: Int) = openDotaApiService.getMatches(teamId)
}