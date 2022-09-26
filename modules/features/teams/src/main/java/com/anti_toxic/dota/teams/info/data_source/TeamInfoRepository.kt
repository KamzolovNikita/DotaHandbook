package com.anti_toxic.dota.teams.info.data_source

import javax.inject.Inject

class TeamInfoRepository @Inject constructor(
    private val teamPlayersMapper: TeamPlayersMapper,
    private val teamMatchesMapper: TeamMatchesMapper,
    private val teamInfoApi: TeamInfoApi,
    private val teamInfoCache: TeamInfoCache
) {



}