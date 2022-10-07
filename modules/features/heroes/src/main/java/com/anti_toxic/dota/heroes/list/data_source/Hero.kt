package com.anti_toxic.dota.heroes.list.data_source

import com.anti_toxic.dota.core_api.AttackType
import com.anti_toxic.dota.core_api.Attribute
import com.anti_toxic.dota.core_api.Role

data class Hero(
    val name: String,
    val primaryAttribute: Attribute,
    val attackType: AttackType,
    val roles: List<Role>,
    val imageUrl: String
)