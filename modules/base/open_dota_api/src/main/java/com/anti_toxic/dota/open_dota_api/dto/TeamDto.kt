package com.anti_toxic.dota.open_dota_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    @SerialName("team_id")
    val teamId: Int,
    @SerialName("rating")
    val rating: Double,
    @SerialName("wins")
    val wins: Int,
    @SerialName("losses")
    val losses: Int,
    @SerialName("name")
    val name: String,
    @SerialName("logo_url")
    val logoUrl: String?
)