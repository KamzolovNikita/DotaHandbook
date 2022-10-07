package com.anti_toxic.dota.databases.items

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anti_toxic.dota.databases.StringListConverter

@Database(
    entities = [
        ItemEntity::class
    ],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao

    companion object {
        const val NAME = "items"
    }
}