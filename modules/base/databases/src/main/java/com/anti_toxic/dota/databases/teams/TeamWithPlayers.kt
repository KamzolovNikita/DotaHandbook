package com.anti_toxic.dota.databases.teams

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamWithPlayers(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "team_id",
        entityColumn = "account_id",
        associateBy = Junction(TeamPlayerCrossRef::class)
    )
    val players: List<PlayerEntity>
)