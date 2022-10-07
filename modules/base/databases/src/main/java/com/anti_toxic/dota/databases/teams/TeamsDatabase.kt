package com.anti_toxic.dota.databases.teams

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.anti_toxic.dota.databases.RoleListConverter

@Database(
    entities = [
        TeamEntity::class,
        PlayerEntity::class,
        TeamPlayerCrossRef::class,
        MatchEntity::class,
        TeamMatchCrossRef::class
    ],
    version = 1
)
abstract class TeamsDatabase : RoomDatabase() {
    abstract fun teamsDao(): TeamsDao

    companion object {
        const val NAME = "heroes_list"
    }
}