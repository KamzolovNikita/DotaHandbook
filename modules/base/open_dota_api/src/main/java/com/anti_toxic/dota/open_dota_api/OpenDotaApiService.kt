package com.anti_toxic.dota.open_dota_api

import com.anti_toxic.dota.open_dota_api.dto.HeroDto
import com.anti_toxic.dota.open_dota_api.dto.ItemDto
import com.anti_toxic.dota.open_dota_api.dto.MatchDto
import com.anti_toxic.dota.open_dota_api.dto.PlayerDto
import com.anti_toxic.dota.open_dota_api.dto.TeamDto
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenDotaApiService {

    companion object {
        const val BASE_URL = "https://api.opendota.com/api/"
        const val BASE_IMAGE_RESOURCES_URL = "https://cdn.cloudflare.steamstatic.com"
    }

    @GET("teams")
    suspend fun getTeams(): List<TeamDto>

    @GET("teams/{teamId}")
    suspend fun getTeam(@Path(value = "teamId") teamId: Int): TeamDto

    @GET("teams/{teamId}/players")
    suspend fun getPlayers(@Path(value = "teamId") teamId: Int): List<PlayerDto>

    @GET("teams/{teamId}/matches")
    suspend fun getMatches(@Path(value = "teamId") teamId: Int): List<MatchDto>

    @GET("constants/heroes")
    suspend fun getHeroes(): Map<String, HeroDto>

    @GET("constants/items")
    suspend fun getItems(): Map<String, ItemDto>
}