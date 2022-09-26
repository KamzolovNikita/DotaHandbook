package com.anti_toxic.dota.open_dota_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Player(
    @SerialName("account_id")
    val accountId: Int,
    @SerialName("name")
    val name: String = "",
    @SerialName("games_played")
    val gamesPlayed: Int = 0,
    @SerialName("wins")
    val wins: Int = 0,
    @SerialName("is_current_team_member")
    val isCurrentTeamMember: Boolean = false,
)