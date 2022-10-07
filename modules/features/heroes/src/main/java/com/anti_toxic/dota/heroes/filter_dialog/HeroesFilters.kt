package com.anti_toxic.dota.heroes.filter_dialog

import com.anti_toxic.dota.core_api.AttackType
import com.anti_toxic.dota.core_api.Attribute
import com.anti_toxic.dota.core_api.Role

data class HeroesFilters(
    val attackType: AttackType? = null,
    val role: Role? = null,
    val primaryAttribute: Attribute? = null
)