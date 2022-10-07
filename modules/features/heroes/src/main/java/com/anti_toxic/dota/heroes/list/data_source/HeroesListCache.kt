package com.anti_toxic.dota.heroes.list.data_source

import com.anti_toxic.dota.databases.heroes.HeroEntity
import com.anti_toxic.dota.databases.heroes.HeroesDatabase
import javax.inject.Inject

class HeroesListCache @Inject constructor(
    heroesDatabase: HeroesDatabase
) {
    private val dao = heroesDatabase.heroesDao()

    suspend fun getHeroes(): List<HeroEntity> {
        return dao.getAllHeroes()
    }

    suspend fun updateCache(heroes: List<HeroEntity>) {
        return dao.updateHeroesData(heroes)
    }
}