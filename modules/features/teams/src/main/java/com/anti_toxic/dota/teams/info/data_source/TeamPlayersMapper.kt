package com.anti_toxic.dota.teams.info.data_source

import com.anti_toxic.dota.databases.teams.PlayerEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.PlayerDto

object TeamPlayersMapper: Mapper<List<PlayerDto>, List<PlayerEntity>, List<Player>> {
    override fun entityToDomain(entity: List<PlayerEntity>): List<Player> {
        return entity.map {
            Player(
                accountId = it.accountId,
                name = it.name,
                gamesPlayed = it.gamesPlayed,
                winsPercentage = it.winsPercentage
            )
        }
    }

    override fun dtoToDomain(dto: List<PlayerDto>): List<Player> {
        return dto.map {
            Player(
                accountId = it.accountId,
                name = it.name,
                gamesPlayed = it.gamesPlayed,
                winsPercentage = it.wins * 100f / it.gamesPlayed
            )
        }
    }

    override fun dtoToEntity(dto: List<PlayerDto>): List<PlayerEntity> {
        return dto.map {
            PlayerEntity(
                accountId = it.accountId,
                name = it.name,
                gamesPlayed = it.gamesPlayed,
                winsPercentage = it.wins * 100f / it.gamesPlayed
            )
        }
    }
}