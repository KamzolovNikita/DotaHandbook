package com.anti_toxic.dota.databases.items

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class ItemsDao {

    @Query("SELECT * FROM items")
    abstract suspend fun getAllItems(): List<ItemEntity>

    suspend fun updateItemsData(items: List<ItemEntity>) {
        removeOldItemsData()
        insertAllItems(items)
    }

    @Insert
    protected abstract suspend fun insertAllItems(items: List<ItemEntity>)

    @Query("DELETE FROM items")
    protected abstract suspend fun removeOldItemsData()
}