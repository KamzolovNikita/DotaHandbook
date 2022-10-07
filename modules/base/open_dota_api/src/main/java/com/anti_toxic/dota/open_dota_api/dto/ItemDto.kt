package com.anti_toxic.dota.open_dota_api.dto

import com.anti_toxic.dota.open_dota_api.OpenDotaApiService
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ItemDto(
    @SerialName("id")
    val itemId: Int,
    @SerialName("dname")
    val name: String = "",
    @SerialName("cost")
    val cost: Int = -1,
    @SerialName("hint")
    val hints: List<String> = emptyList(),
    @SerialName("cd")
    val cooldown: JsonElement,
    @SerialName("lore")
    val lore: String
) {
    @SerialName("img")
    val imageUrl: String = ""
        get() = "${OpenDotaApiService.BASE_IMAGE_RESOURCES_URL}$field"
}