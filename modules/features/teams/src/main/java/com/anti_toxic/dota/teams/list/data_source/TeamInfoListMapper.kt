package com.anti_toxic.dota.teams.list.data_source

import com.anti_toxic.dota.databases.teams.TeamEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.Team
import dagger.Reusable
import javax.inject.Inject

@Reusable
class TeamInfoListMapper @Inject constructor(): Mapper<List<Team>, List<TeamEntity>> {

    override fun toEntity(value: List<Team>): List<TeamEntity> {
        return value.map {
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

    override fun toDomain(value: List<TeamEntity>): List<Team> {
        return value.map {
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
}