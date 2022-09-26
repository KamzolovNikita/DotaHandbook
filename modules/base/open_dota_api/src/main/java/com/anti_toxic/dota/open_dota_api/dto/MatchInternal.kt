package com.anti_toxic.dota.open_dota_api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchInternal(
    @SerialName("match_id")
    val matchId: Int,
    @SerialName("duration")
    val duration: Int,
    @SerialName("start_time")
    val startTime: Long,
    @SerialName("opposing_team_name")
    val opposingTeamName: String,
    @SerialName("opposing_team_logo")
    val opposingTeamLogoUrl: String?,
    @SerialName("radiant")
    val isRadiant: Boolean,
    @SerialName("radiant_win")
    val isRadiantWin: Boolean,
    @SerialName("league_name")
    val leagueName: String?
)