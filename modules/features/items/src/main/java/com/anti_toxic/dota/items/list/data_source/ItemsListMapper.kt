package com.anti_toxic.dota.items.list.data_source

import com.anti_toxic.dota.databases.items.ItemEntity
import com.anti_toxic.dota.network.Mapper
import com.anti_toxic.dota.open_dota_api.dto.ItemDto
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull

object ItemsListMapper : Mapper<List<ItemDto>, List<ItemEntity>, List<Item>> {
    override fun entityToDomain(entity: List<ItemEntity>): List<Item> {
        return entity.map {
            Item(
                itemId = it.itemId,
                name = it.name,
                cost = it.cost,
                hints = it.hints,
                cooldown = it.cooldown,
                lore = it.lore,
                imageUrl = it.imageUrl
            )
        }
    }

    override fun dtoToDomain(dto: List<ItemDto>): List<Item> {
        return dto
            .filter { it.name.isNotEmpty() || it.cost != -1 }
            .map {
                Item(
                    itemId = it.itemId,
                    name = it.name,
                    cost = it.cost,
                    hints = it.hints,
                    cooldown = (it.cooldown as? JsonPrimitive)?.intOrNull,
                    lore = it.lore,
                    imageUrl = it.imageUrl
                )
            }
    }

    override fun dtoToEntity(dto: List<ItemDto>): List<ItemEntity> {
        return dto
            .filter { it.name.isNotEmpty() || it.cost != -1 }
            .map {
                ItemEntity(
                    itemId = it.itemId,
                    name = it.name,
                    cost = it.cost,
                    hints = ArrayList(it.hints),
                    cooldown = (it.cooldown as? JsonPrimitive)?.intOrNull,
                    lore = it.lore,
                    imageUrl = it.imageUrl
                )
            }
    }
}