package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.core_api.Side

data class Match(
    val matchId: Long,
    val duration: Long,
    val startTime: Long,
    val opposingTeamName: String,
    val opposingTeamLogoUrl: String?,
    val isWin: Boolean,
    val side: Side,
    val leagueName: String?
)