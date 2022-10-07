package com.anti_toxic.dota.heroes.list.data_source

import com.anti_toxic.dota.databases.heroes.HeroEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.HeroDto

object HeroesListMapper: Mapper<List<HeroDto>, List<HeroEntity>, List<Hero>> {
    override fun entityToDomain(entity: List<HeroEntity>): List<Hero> {
        return entity.map {
            Hero(
                name = it.name,
                primaryAttribute = it.primaryAttribute,
                attackType = it.attackType,
                roles = it.roles,
                imageUrl = it.imageUrl
            )
        }
    }

    override fun dtoToDomain(dto: List<HeroDto>): List<Hero> {
        return dto.map {
            Hero(
                name = it.name,
                primaryAttribute = it.primaryAttribute,
                attackType = it.attackType,
                roles = it.roles,
                imageUrl = it.imageUrl
            )
        }
    }

    override fun dtoToEntity(dto: List<HeroDto>): List<HeroEntity> {
        return dto.map {
            HeroEntity(
                name = it.name,
                primaryAttribute = it.primaryAttribute,
                attackType = it.attackType,
                roles = ArrayList(it.roles),
                imageUrl = it.imageUrl
            )
        }
    }
}