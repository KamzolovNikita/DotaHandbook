package com.anti_toxic.dota.heroes.list.data_source

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.network.request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HeroesListRepository @Inject constructor(
    private val heroesListCache: HeroesListCache,
    private val heroesListApi: HeroesListApi
) {

    suspend fun getHeroes(): Flow<Resource<List<Hero>>> {
        return request(
            fetchCache = { heroesListCache.getHeroes().takeIf { it.isNotEmpty() } },
            fetchRemote = { heroesListApi.getHeroes().values.toList() },
            updateCache = { value -> heroesListCache.updateCache(value) },
            mapper = HeroesListMapper,
            forceUpdate = false
        )
    }
}