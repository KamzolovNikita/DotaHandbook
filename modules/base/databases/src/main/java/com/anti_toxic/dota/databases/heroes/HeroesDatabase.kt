package com.anti_toxic.dota.databases.heroes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anti_toxic.dota.databases.RoleListConverter

@Database(
    entities = [
        HeroEntity::class
    ],
    version = 1
)
@TypeConverters(RoleListConverter::class)
abstract class HeroesDatabase : RoomDatabase() {
    abstract fun heroesDao(): HeroesDao

    companion object {
        const val NAME = "heroes"
    }
}