package com.anti_toxic.dota.core_api

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttackType(@StringRes val localizedName: Int) {
    @SerialName("Melee")
    MELEE(R.string.attack_type_melee),
    @SerialName("Ranged")
    RANGED(R.string.attack_type_ranged)
}