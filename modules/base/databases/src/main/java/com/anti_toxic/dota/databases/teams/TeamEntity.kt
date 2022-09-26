package com.anti_toxic.dota.databases.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    @ColumnInfo(name = "team_id")
    val teamId: Int,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "wins")
    val wins: Int,
    @ColumnInfo(name = "losses")
    val losses: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "logo")
    val logoUrl: String?
)