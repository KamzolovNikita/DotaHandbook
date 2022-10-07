package com.anti_toxic.dota.teams.info.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.teams.databinding.TeamInfoHeaderItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoMatchItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoPlayerItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoTeamItemBinding
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewHolder.HeaderViewHolder
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewHolder.MatchViewHolder
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewHolder.PlayerViewHolder
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewHolder.TeamViewHolder
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.HeaderItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.MatchItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.PlayerItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.TeamItem

class TeamInfoAdapter : RecyclerView.Adapter<TeamInfoViewHolder>() {

    var items: List<TeamInfoViewItem> = emptyList()
        set(value) {
            val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return field.size
                }

                override fun getNewListSize(): Int {
                    return value.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return DIFF_CALLBACK.areItemsTheSame(
                        field[oldItemPosition],
                        value[newItemPosition]
                    )
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return DIFF_CALLBACK.areContentsTheSame(
                        field[oldItemPosition],
                        value[newItemPosition]
                    )
                }
            }, true)
            field = value
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamInfoViewHolder {
        return when (viewType) {
            HEADER_ITEM -> {
                HeaderViewHolder(
                    TeamInfoHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TEAM_ITEM -> {
                TeamViewHolder(
                    TeamInfoTeamItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            MATCH_ITEM -> {
                MatchViewHolder(
                    TeamInfoMatchItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            PLAYER_ITEM -> {
                PlayerViewHolder(
                    TeamInfoPlayerItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid TeamInfo ViewType")
            }
        }
    }

    override fun onBindViewHolder(holder: TeamInfoViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as HeaderItem)
            is TeamViewHolder -> holder.bind(items[position] as TeamItem)
            is MatchViewHolder -> holder.bind(items[position] as MatchItem)
            is PlayerViewHolder -> holder.bind(items[position] as PlayerItem)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HeaderItem -> HEADER_ITEM
            is TeamItem -> TEAM_ITEM
            is MatchItem -> MATCH_ITEM
            is PlayerItem -> PLAYER_ITEM
        }
    }

    companion object {
        private const val HEADER_ITEM = 0
        private const val TEAM_ITEM = 1
        private const val MATCH_ITEM = 2
        private const val PLAYER_ITEM = 3

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TeamInfoViewItem>() {
            override fun areItemsTheSame(oldItem: TeamInfoViewItem, newItem: TeamInfoViewItem): Boolean {
                return when {
                    oldItem is TeamItem && newItem is TeamItem -> {
                        true
                    }
                    oldItem is HeaderItem && newItem is HeaderItem -> {
                        oldItem.text == oldItem.text
                    }
                    oldItem is MatchItem && newItem is MatchItem -> {
                        oldItem.value.matchId == newItem.value.matchId
                    }
                    oldItem is PlayerItem && newItem is PlayerItem -> {
                        oldItem.value.accountId == newItem.value.accountId
                    }
                    else -> {
                        false
                    }
                }
            }

            override fun areContentsTheSame(oldItem: TeamInfoViewItem, newItem: TeamInfoViewItem): Boolean {
                return when {
                    oldItem is TeamItem && newItem is TeamItem -> {
                        oldItem == newItem
                    }
                    oldItem is HeaderItem && newItem is HeaderItem -> {
                        oldItem.text == oldItem.text
                            && oldItem.state.isAssignableFrom(newItem.state)
                    }
                    oldItem is MatchItem && newItem is MatchItem -> {
                        oldItem.value == newItem.value
                    }
                    oldItem is PlayerItem && newItem is PlayerItem -> {
                        oldItem.value == newItem.value
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}