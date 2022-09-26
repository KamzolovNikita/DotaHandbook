package com.anti_toxic.dota.teams.list.data_source

import com.anti_toxic.dota.core_api.Constants
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.network.request
import com.anti_toxic.dota.open_dota_api.dto.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamsListRepository @Inject constructor(
    private val teamsListMapper: TeamInfoListMapper,
    private val teamsListApi: TeamsListApi,
    private val teamsListCache: TeamsListCache
) {

    suspend fun getTeamsList(forceUpdate: Boolean): Flow<Resource<List<Team>>> {
        return request(
            fetchCache = { teamsListCache.getTeams().takeIf { it.isNotEmpty() } },
            fetchRemote = { teamsListApi.getTeams().take(Constants.MAX_TEAMS_IN_TEAMS_LIST) },
            updateCache = { value -> teamsListCache.updateCache(value) },
            mapper = teamsListMapper,
            forceUpdate = forceUpdate
        )
    }
}