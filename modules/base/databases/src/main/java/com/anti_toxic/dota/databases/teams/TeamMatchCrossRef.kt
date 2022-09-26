package com.anti_toxic.dota.databases.teams

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["team_id", "match_key"],
    tableName = "team_with_matches"
)
data class TeamMatchCrossRef(
    @ColumnInfo(name = "team_id", index = true)
    val teamId: Int,
    @ColumnInfo(name = "match_key")
    val matchKey: String,
)