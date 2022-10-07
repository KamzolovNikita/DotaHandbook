package com.anti_toxic.dota.open_dota_api.dto

import com.anti_toxic.dota.core_api.AttackType
import com.anti_toxic.dota.core_api.Attribute
import com.anti_toxic.dota.core_api.Role
import com.anti_toxic.dota.open_dota_api.OpenDotaApiService.Companion.BASE_IMAGE_RESOURCES_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroDto(
    @SerialName("localized_name")
    val name: String,
    @SerialName("primary_attr")
    val primaryAttribute: Attribute,
    @SerialName("attack_type")
    val attackType: AttackType,
    @SerialName("roles")
    val roles: List<Role>
): java.io.Serializable {

    @SerialName("img")
    val imageUrl: String = ""
        get() = "$BASE_IMAGE_RESOURCES_URL$field"
}



