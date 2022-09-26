package com.anti_toxic.dota.databases.teams

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["team_id", "account_id"],
    tableName = "team_with_players"
)
data class TeamPlayerCrossRef(
    @ColumnInfo(name = "team_id", index = true)
    val teamId: Int,
    @ColumnInfo(name = "account_id")
    val accountId: Int
)