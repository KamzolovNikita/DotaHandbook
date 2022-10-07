package com.anti_toxic.dota.teams.list.data_source

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val teamId: Int,
    val rating: Double,
    val wins: Int,
    val losses: Int,
    val name: String,
    val logoUrl: String?
): java.io.Serializable