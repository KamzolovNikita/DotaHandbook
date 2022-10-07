package com.anti_toxic.dota.teams.info

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.teams.info.data_source.Match
import com.anti_toxic.dota.teams.info.data_source.Player
import com.anti_toxic.dota.teams.info.data_source.TeamInfoRepository
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem
import com.anti_toxic.dota.teams.list.data_source.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TeamInfoInteractor @Inject constructor(
    private val repository: TeamInfoRepository,
    private val teamInfoViewItemsFactory: TeamInfoViewItemsFactory
) {
    private var teamFallback: Resource<Team>? = null
    private var matchesFallback: Resource<List<Match>>? = null
    private var playersFallback: Resource<List<Player>>? = null

    suspend fun getTeamInfoViewItemsFlow(
        team: Team,
        isRefresh: Boolean
    ): Flow<Resource<List<TeamInfoViewItem>>> {
        return combine(
            getTeam(team, isRefresh),
            getPlayers(team.teamId, isRefresh),
            getMatches(team.teamId, isRefresh),
            teamInfoViewItemsFactory::create
        ).distinctUntilChanged()
    }

    private suspend fun getTeam(team: Team, isRefresh: Boolean): Flow<Resource<Team>> {
        val teamFlow = if (isRefresh) {
            fetchFlowWithSuccessFallback(this::teamFallback) {
                repository.getTeamInfo(team.teamId)
            }
        } else {
            flowOf(Success(team))
        }

        return teamFlow.onEach {
            if (it is Success) {
                teamFallback = it
            }
        }
    }

    private suspend fun getPlayers(teamId: Int, isRefresh: Boolean): Flow<Resource<List<Player>>> {
        return fetchFlowWithSuccessFallback(this::playersFallback) {
            repository.getPlayers(teamId, isRefresh)
        }.onEach {
            if (it is Success) {
                playersFallback = it
            }
        }
    }

    private suspend fun getMatches(teamId: Int, isRefresh: Boolean): Flow<Resource<List<Match>>> {
        return fetchFlowWithSuccessFallback(this::matchesFallback) {
            repository.getMatches(teamId, isRefresh)
        }.onEach {
            if (it is Success) {
                matchesFallback = it
            }
        }
    }

    private suspend inline fun <T> fetchFlowWithSuccessFallback(
        crossinline getFallback: () -> Resource<T>?,
        crossinline fetch: suspend () -> Flow<Resource<T>>
    ): Flow<Resource<T>> {
        return fetch()
            .map { response ->
                when (response) {
                    is Empty,
                    is Error,
                    is Loading -> {
                        getFallback() ?: response
                    }
                    is Success -> {
                        response
                    }
                }
            }
    }
}

