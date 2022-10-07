package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.databases.teams.TeamEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.TeamDto
import com.anti_toxic.dota.teams.list.data_source.Team

object TeamInfoMapper: Mapper<TeamDto, TeamEntity, Team> {
    override fun entityToDomain(entity: TeamEntity): Team {
        with(entity) {
            return Team(
                teamId = teamId,
                rating = rating,
                wins = wins,
                losses = losses,
                name = name,
                logoUrl = logoUrl
            )
        }
    }

    override fun dtoToDomain(dto: TeamDto): Team {
        with(dto) {
            return Team(
                teamId = teamId,
                rating = rating,
                wins = wins,
                losses = losses,
                name = name,
                logoUrl = logoUrl
            )
        }
    }

    override fun dtoToEntity(dto: TeamDto): TeamEntity {
        with(dto) {
            return TeamEntity(
                teamId = teamId,
                rating = rating,
                wins = wins,
                losses = losses,
                name = name,
                logoUrl = logoUrl
            )
        }
    }
}