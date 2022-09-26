package com.anti_toxic.dota.teams.list.data_source

import com.anti_toxic.dota.core_api.Side
import com.anti_toxic.dota.databases.teams.MatchEntity
import com.anti_toxic.dota.databases.teams.PlayerEntity
import com.anti_toxic.dota.databases.teams.TeamEntity
import com.anti_toxic.dota.databases.teams.TeamsDatabase
import javax.inject.Inject

class TeamsListCache @Inject constructor(
    teamsDatabase: TeamsDatabase
) {
    private val dao = teamsDatabase.teamsDao()

    suspend fun getTeams(): List<TeamEntity> {
        return dao.getAllTeams()
    }

    suspend fun updateCache(teams: List<TeamEntity>) {
        return dao.updateTeamsData(teams)
    }
}