package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.core_api.Side
import com.anti_toxic.dota.open_dota_api.dto.MatchInternal

data class Match(
    val matchId: Int,
    val duration: Int,
    val startTime: Long,
    val opposingTeamName: String,
    val opposingTeamLogoUrl: String?,
    val isWin: Boolean,
    val side: Side,
    val leagueName: String?
)

fun MatchInternal.mapToDomain(): Match {
    return Match(
        matchId = this.matchId,
        duration = this.duration,
        startTime = this.startTime,
        opposingTeamName = this.opposingTeamName,
        opposingTeamLogoUrl = this.opposingTeamLogoUrl,
        isWin = this.isRadiant == this.isRadiantWin,
        side = if (this.isRadiant) {
            Side.RADIANT
        } else {
            Side.DIRE
        },
        leagueName = this.leagueName
    )
}