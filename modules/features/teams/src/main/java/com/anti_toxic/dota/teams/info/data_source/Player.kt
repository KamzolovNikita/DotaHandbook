package com.anti_toxic.dota.teams.info.data_source

data class Player(
    val accountId: Int,
    val name: String = "",
    val gamesPlayed: Int = 0,
    val winsPercentage: Float = 0f
)
