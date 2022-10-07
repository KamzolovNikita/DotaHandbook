package com.anti_toxic.dota.databases.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "account_id")
    val accountId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "games_played")
    val gamesPlayed: Int,
    @ColumnInfo(name = "wins_percentage")
    val winsPercentage: Float
)