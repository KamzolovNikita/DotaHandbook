package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.network.request
import com.anti_toxic.dota.teams.list.data_source.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamInfoRepository @Inject constructor(
    private val teamInfoApi: TeamInfoApi,
    private val teamInfoCache: TeamInfoCache
) {

    suspend fun getPlayers(teamId: Int, forceUpdate: Boolean): Flow<Resource<List<Player>>> {
        return request(
            fetchCache = { teamInfoCache.getPlayers(teamId).takeIf { it.isNotEmpty() } },
            fetchRemote = { teamInfoApi.getPlayers(teamId).filter { it.isCurrentTeamMember } },
            updateCache = { players -> teamInfoCache.updatePlayers(players, teamId) },
            mapper = TeamPlayersMapper,
            forceUpdate = forceUpdate
        )
    }

    suspend fun getMatches(teamId: Int, forceUpdate: Boolean): Flow<Resource<List<Match>>> {
        return request(
            fetchCache = { teamInfoCache.getMatches(teamId).takeIf { it.isNotEmpty() } },
            fetchRemote = { teamInfoApi.getMatches(teamId).sortedByDescending { it.startTime }.take(20) },
            updateCache = { matches -> teamInfoCache.updateMatches(matches, teamId) },
            mapper = TeamMatchesMapper,
            forceUpdate = forceUpdate
        )
    }

    suspend fun getTeamInfo(teamId: Int): Flow<Resource<Team>> {
        return request(
            fetchCache = { teamInfoCache.getTeam(teamId) },
            fetchRemote = { teamInfoApi.getTeam(teamId) },
            updateCache = { team -> teamInfoCache.updateTeam(team) },
            mapper = TeamInfoMapper,
            forceUpdate = true
        )
    }
}