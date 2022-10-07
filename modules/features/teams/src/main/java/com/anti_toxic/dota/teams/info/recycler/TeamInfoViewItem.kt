package com.anti_toxic.dota.teams.info.recycler

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.teams.info.data_source.Match
import com.anti_toxic.dota.teams.info.data_source.Player
import com.anti_toxic.dota.teams.list.data_source.Team

sealed class TeamInfoViewItem {

    class TeamItem(val value: Resource<Team>) : TeamInfoViewItem()

    class HeaderItem(
        val state: Class<out Resource<*>>,
        val text: String
    ) : TeamInfoViewItem()

    class MatchItem(
        val value: Match
    ) : TeamInfoViewItem()

    class PlayerItem(
        val value: Player
    ) : TeamInfoViewItem()
}