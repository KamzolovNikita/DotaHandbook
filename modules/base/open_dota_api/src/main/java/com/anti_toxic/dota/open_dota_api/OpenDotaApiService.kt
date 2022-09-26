package com.anti_toxic.dota.open_dota_api

import com.anti_toxic.dota.open_dota_api.dto.MatchInternal
import com.anti_toxic.dota.open_dota_api.dto.Player
import com.anti_toxic.dota.open_dota_api.dto.Team
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenDotaApiService {

    companion object {
        const val BASE_URL = "https://api.opendota.com/api/"
    }

    @GET("teams")
    suspend fun getTeams(): List<Team>

    @GET("teams/{teamId}/players")
    suspend fun getPlayers(@Path(value = "teamId") teamId: Int): List<Player>

    @GET("teams/{teamId}/matches")
    suspend fun getMatches(@Path(value = "teamId") teamId: Int): List<MatchInternal>
}