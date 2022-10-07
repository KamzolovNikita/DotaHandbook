package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.databases.teams.MatchEntity
import com.anti_toxic.dota.databases.teams.PlayerEntity
import com.anti_toxic.dota.databases.teams.TeamEntity
import com.anti_toxic.dota.databases.teams.TeamsDatabase
import javax.inject.Inject

class TeamInfoCache @Inject constructor(
    teamsDatabase: TeamsDatabase
) {
    private val dao = teamsDatabase.teamsDao()

    suspend fun getPlayers(teamId: Int) = dao.getPlayersOfTeamById(teamId)

    suspend fun getMatches(teamId: Int) = dao.getMatchesOfTeamById(teamId)

    suspend fun getTeam(teamId: Int) = dao.getTeam(teamId)

    suspend fun updatePlayers(players: List<PlayerEntity>,  teamId: Int) = dao.updatePlayersData(players, teamId)

    suspend fun updateMatches(matches: List<MatchEntity>, teamId: Int) = dao.updateMatchesData(matches, teamId)

    suspend fun updateTeam(team: TeamEntity) = dao.updateTeam(team)
}