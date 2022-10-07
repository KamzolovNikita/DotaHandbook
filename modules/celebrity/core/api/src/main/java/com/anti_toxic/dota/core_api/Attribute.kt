package com.anti_toxic.dota.core_api

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Attribute(@StringRes val localizedName: Int) {
    @SerialName("agi")
    AGILITY(R.string.attribute_agility),
    @SerialName("str")
    STRENGTH(R.string.attribute_strength),
    @SerialName("int")
    INTELLIGENCE(R.string.attribute_intelligence),
}