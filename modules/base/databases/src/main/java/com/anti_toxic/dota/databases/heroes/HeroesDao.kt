package com.anti_toxic.dota.databases.heroes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class HeroesDao {

    @Query("SELECT * FROM heroes")
    abstract suspend fun getAllHeroes(): List<HeroEntity>

    suspend fun updateHeroesData(heroes: List<HeroEntity>) {
        removeOldHeroesData()
        insertAllHeroes(heroes)
    }

    @Insert
    protected abstract suspend fun insertAllHeroes(items: List<HeroEntity>)

    @Query("DELETE FROM heroes")
    protected abstract suspend fun removeOldHeroesData()
}