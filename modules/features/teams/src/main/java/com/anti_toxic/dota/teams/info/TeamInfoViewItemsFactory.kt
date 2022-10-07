package com.anti_toxic.dota.teams.info

import android.content.res.Resources
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.teams.R
import com.anti_toxic.dota.teams.info.data_source.Match
import com.anti_toxic.dota.teams.info.data_source.Player
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.MatchItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.PlayerItem
import com.anti_toxic.dota.teams.list.data_source.Team
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class TeamInfoViewItemsFactory @Inject constructor(
    private val resource: Resources
) {

    fun create(
        teamResource: Resource<Team>,
        playersResource: Resource<List<Player>>,
        matchesResource: Resource<List<Match>>
    ): Resource<List<TeamInfoViewItem>> {
        val teamInfo = TeamInfo(teamResource, playersResource, matchesResource)

        when {
            isAllResourcesStated(teamInfo, Empty::class.java) -> return Empty()
            isAllResourcesStated(teamInfo, Error::class.java) -> {
                val throwable = IOException("All TeamInfo loadings failed")
                Timber.e(throwable)
                return Error(throwable)
            }
        }

        return if (isAtLeastOneResourceStated(teamInfo, Success::class.java)) {
            Success(createTeamInfoViewItemList(teamInfo))
        } else {
            Loading()
        }
    }

    private fun isAllResourcesStated(teamInfo: TeamInfo, state: Class<out Resource<*>>): Boolean {
        return teamInfo.team.javaClass == state
            && teamInfo.players.javaClass == state
            && teamInfo.matches.javaClass == state
    }

    private fun isAtLeastOneResourceStated(teamInfo: TeamInfo, state: Class<out Resource<*>>): Boolean {
        return teamInfo.team.javaClass == state
            || teamInfo.players.javaClass == state
            || teamInfo.matches.javaClass == state
    }

    private fun createTeamInfoViewItemList(teamInfo: TeamInfo): List<TeamInfoViewItem> {
        val resultList = mutableListOf<TeamInfoViewItem>()

        resultList.add(TeamInfoViewItem.TeamItem(teamInfo.team))
        resultList.addPlayers(teamInfo)
        resultList.addMatches(teamInfo)

        return resultList
    }

    private fun MutableList<TeamInfoViewItem>.addPlayers(teamInfo: TeamInfo) {
        val playersResource = teamInfo.players
        if (playersResource is Success && playersResource.data.isNotEmpty()) {
            add(
                TeamInfoViewItem.HeaderItem(
                    playersResource.javaClass,
                    resource.getString(R.string.header_players)
                )
            )
            addAll(playersResource.data.map { PlayerItem(it) })
        } else if (playersResource is Loading || playersResource is Error) {
            add(
                TeamInfoViewItem.HeaderItem(
                    playersResource.javaClass,
                    resource.getString(R.string.header_players)
                )
            )
        }
    }

    private fun MutableList<TeamInfoViewItem>.addMatches(teamInfo: TeamInfo) {
        val matchesResource = teamInfo.matches
        if (matchesResource is Success && matchesResource.data.isNotEmpty()) {
            add(
                TeamInfoViewItem.HeaderItem(
                    matchesResource.javaClass,
                    resource.getString(R.string.header_last_matches)
                )
            )
            addAll(matchesResource.data.map { MatchItem(it) })
        } else if (matchesResource is Loading  || matchesResource is Error) {
            add(
                TeamInfoViewItem.HeaderItem(
                    matchesResource.javaClass,
                    resource.getString(R.string.header_last_matches)
                )
            )
        }
    }

    private inner class TeamInfo(
        val team: Resource<Team>,
        val players: Resource<List<Player>>,
        val matches: Resource<List<Match>>
    )
}