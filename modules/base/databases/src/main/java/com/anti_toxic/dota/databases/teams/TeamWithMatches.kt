package com.anti_toxic.dota.databases.teams

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamWithMatches(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "team_id",
        entityColumn = "match_key",
        associateBy = Junction(TeamMatchCrossRef::class)
    )
    val matches: List<MatchEntity>
)