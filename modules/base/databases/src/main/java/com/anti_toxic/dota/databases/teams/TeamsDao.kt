package com.anti_toxic.dota.databases.teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.anti_toxic.dota.core_api.Constants

@Dao
abstract class TeamsDao {

    @Query("SELECT * FROM teams ORDER BY rating DESC")
    abstract suspend fun getAllTeams(): List<TeamEntity>

    @Transaction
    @Query("SELECT * FROM players WHERE account_id IN (SELECT account_id FROM team_with_players WHERE team_id=:id)")
    abstract suspend fun getPlayersOfTeamById(id: Int): List<PlayerEntity>

    @Transaction
    @Query("SELECT * FROM matches WHERE match_key IN (SELECT match_key FROM team_with_matches WHERE team_id=:id)")
    abstract suspend fun getMatchesOfTeamById(id: Int): List<MatchEntity>

    suspend fun updateTeamsData(teams: List<TeamEntity>) {
        insertAllTeams(teams)
        removeOldTeamsData(Constants.MAX_TEAMS_IN_TEAMS_LIST)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAllTeams(items: List<TeamEntity>)

    @Query("DELETE FROM teams WHERE team_id NOT IN (SELECT team_id FROM teams ORDER BY rating DESC LIMIT :limit)")
    protected abstract suspend fun removeOldTeamsData(limit: Int)

    suspend fun updatePlayersData(players: List<PlayerEntity>, teamId: Int) {
        insertAllPlayers(players)
        players.forEach { player ->
            insertTeamWithPlayerEntity(
                TeamPlayerCrossRef(
                    teamId = teamId,
                    accountId = player.accountId
                )
            )
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAllPlayers(items: List<PlayerEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertTeamWithPlayerEntity(item: TeamPlayerCrossRef)

    suspend fun updateMatchesData(matches: List<MatchEntity>, teamId: Int) {
        insertAllMatches(matches)
        matches.forEach { match ->
            insertTeamWithMatchEntity(
                TeamMatchCrossRef(
                    teamId = teamId,
                    matchKey = match.matchKey
                )
            )
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertAllMatches(items: List<MatchEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertTeamWithMatchEntity(item: TeamMatchCrossRef)
}