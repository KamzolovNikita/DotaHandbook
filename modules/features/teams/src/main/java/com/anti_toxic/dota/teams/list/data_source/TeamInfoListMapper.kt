package com.anti_toxic.dota.teams.list.data_source

import com.anti_toxic.dota.databases.teams.TeamEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.TeamDto

object TeamInfoListMapper : Mapper<List<TeamDto>, List<TeamEntity>, List<Team>> {

    override fun entityToDomain(entity: List<TeamEntity>): List<Team> {
        return entity.map {
            Team(
                teamId = it.teamId,
                rating = it.rating,
                wins = it.wins,
                losses = it.losses,
                name = it.name,
                logoUrl = it.logoUrl
            )
        }
    }

    override fun dtoToDomain(dto: List<TeamDto>): List<Team> {
        return dto.map {
            Team(
                teamId = it.teamId,
                rating = it.rating,
                wins = it.wins,
                losses = it.losses,
                name = it.name,
                logoUrl = it.logoUrl
            )
        }
    }

    override fun dtoToEntity(dto: List<TeamDto>): List<TeamEntity> {
        return dto.map {
            TeamEntity(
                teamId = it.teamId,
                rating = it.rating,
                wins = it.wins,
                losses = it.losses,
                name = it.name,
                logoUrl = it.logoUrl
            )
        }
    }
}