package com.anti_toxic.dota.core_api

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role(@StringRes val localizedName: Int) {
    @SerialName("Carry")
    CARRY(R.string.role_carry),
    @SerialName("Escape")
    ESCAPE(R.string.role_escape),
    @SerialName("Nuker")
    NUKER(R.string.role_nuker),
    @SerialName("Pusher")
    PUSHER(R.string.role_pusher),
    @SerialName("Disabler")
    DISABLER(R.string.role_disabler),
    @SerialName("Initiator")
    INITIATOR(R.string.role_initiator),
    @SerialName("Jungler")
    JUNGLER(R.string.role_jungler),
    @SerialName("Support")
    SUPPORT(R.string.role_support),
    @SerialName("Durable")
    DURABLE(R.string.role_durable)
}