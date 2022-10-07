package com.anti_toxic.dota.items.list.data_source

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val itemId: Int,
    val name: String,
    val cost: Int,
    val hints: List<String>,
    val cooldown: Int?,
    val lore: String,
    val imageUrl: String
) : java.io.Serializable