package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.core_api.Side
import com.anti_toxic.dota.core_api.Side.DIRE
import com.anti_toxic.dota.core_api.Side.RADIANT
import com.anti_toxic.dota.databases.teams.MatchEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.MatchDto

object TeamMatchesMapper : Mapper<List<MatchDto>, List<MatchEntity>, List<Match>> {
    override fun entityToDomain(entity: List<MatchEntity>): List<Match> {
        return entity.map {
            Match(
                matchId = it.matchId,
                duration = it.duration,
                startTime = it.startTime,
                opposingTeamName = it.opposingTeamName,
                opposingTeamLogoUrl = it.opposingTeamLogoUrl,
                isWin = it.isWin,
                side = it.side,
                leagueName = it.leagueName
            )
        }
    }

    override fun dtoToDomain(dto: List<MatchDto>): List<Match> {
        return dto.map {
            Match(
                matchId = it.matchId,
                duration = it.duration,
                startTime = it.startTime,
                opposingTeamName = it.opposingTeamName,
                opposingTeamLogoUrl = it.opposingTeamLogoUrl,
                isWin = it.isRadiant == it.isRadiantWin,
                side = if(it.isRadiant) Side.RADIANT else Side.DIRE,
                leagueName = it.leagueName
            )
        }
    }

    override fun dtoToEntity(dto: List<MatchDto>): List<MatchEntity> {
        return dto.map {
            MatchEntity(
                matchId = it.matchId,
                duration = it.duration,
                startTime = it.startTime,
                opposingTeamName = it.opposingTeamName,
                opposingTeamLogoUrl = it.opposingTeamLogoUrl,
                isWin = it.isRadiant == it.isRadiantWin,
                side = if(it.isRadiant) RADIANT else DIRE,
                leagueName = it.leagueName
            )
        }
    }
}