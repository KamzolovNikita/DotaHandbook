package com.anti_toxic.dota.teams.info.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.teams.R
import com.anti_toxic.dota.teams.databinding.TeamInfoHeaderItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoMatchItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoPlayerItemBinding
import com.anti_toxic.dota.teams.databinding.TeamInfoTeamItemBinding
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.HeaderItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.MatchItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.PlayerItem
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem.TeamItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class TeamInfoViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TeamViewHolder(private val binding: TeamInfoTeamItemBinding) : TeamInfoViewHolder(binding) {

        fun bind(team: TeamItem) {
            val resources = binding.root.context.resources
            if (team.value is Success) {
                with(team.value.data) {
                    binding.teamName.text = name
                    binding.teamRating.text = String.format(
                        resources.getString(R.string.team_rating_placeholder),
                        rating
                    )
                    val winsPercentage = (wins * 100f / (wins + losses))
                    binding.winrate.text = String.format(
                        resources.getString(R.string.winrate_placeholder),
                        "%.1f".format(winsPercentage)
                    )
                    if (winsPercentage > 50f) {
                        binding.winrate.setTextColor(
                            resources.getColor(
                                com.anti_toxic.dota.ui.R.color.greenWin,
                                binding.root.context.theme
                            )
                        )
                    } else {
                        binding.winrate.setTextColor(
                            resources.getColor(
                                com.anti_toxic.dota.ui.R.color.redLose,
                                binding.root.context.theme
                            )
                        )
                    }

                    Glide.with(binding.teamLogo)
                        .load(logoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.ic_team)
                        .into(binding.teamLogo)
                }
            }
        }
    }

    class PlayerViewHolder(private val binding: TeamInfoPlayerItemBinding) : TeamInfoViewHolder(binding) {

        fun bind(player: PlayerItem) {
            val resources = binding.root.context.resources
            with(player.value) {
                binding.winrate.text = String.format(
                    resources.getString(R.string.winrate_placeholder),
                    "%.1f".format(winsPercentage)
                )
                if (winsPercentage > 50f) {
                    binding.winrate.setTextColor(
                        resources.getColor(
                            com.anti_toxic.dota.ui.R.color.greenWin,
                            binding.root.context.theme
                        )
                    )
                } else {
                    binding.winrate.setTextColor(
                        resources.getColor(
                            com.anti_toxic.dota.ui.R.color.redLose,
                            binding.root.context.theme
                        )
                    )
                }
                binding.nickname.text = name
                binding.totalGames.text = String.format(
                    resources.getString(R.string.player_games_placeholder),
                    gamesPlayed
                )
            }

        }
    }

    class MatchViewHolder(private val binding: TeamInfoMatchItemBinding) : TeamInfoViewHolder(binding) {

        fun bind(match: MatchItem) {
            val resources = binding.root.context.resources
            with(match.value) {
                var seconds = (duration % 60).toString()
                if (seconds.length == 1) {
                    seconds = "0$seconds"
                }
                binding.duration.text = String.format(
                    resources.getString(R.string.match_duration_placeholder),
                    duration / 60,
                    seconds
                )
                binding.leagueName.text = leagueName
                binding.oppositeTeamName.text = opposingTeamName
                binding.side.text = String.format(
                    resources.getString(R.string.side_placeholder),
                    side.toString()
                )
                val date = Date(startTime)
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                binding.startTime.text = format.format(date)
                if (isWin) {
                    binding.winOrLoseText.text = resources.getString(R.string.win_text)
                    binding.winOrLoseText.setTextColor(
                        resources.getColor(
                            com.anti_toxic.dota.ui.R.color.greenWin,
                            binding.root.context.theme
                        )
                    )
                } else {
                    binding.winOrLoseText.text = resources.getString(R.string.lose_text)
                    binding.winOrLoseText.setTextColor(
                        resources.getColor(
                            com.anti_toxic.dota.ui.R.color.redLose,
                            binding.root.context.theme
                        )
                    )
                }

                Glide.with(binding.oppositeTeamLogo)
                    .load(opposingTeamLogoUrl)
                    .placeholder(resources.getDrawable(R.drawable.ic_team, binding.root.context.theme))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.oppositeTeamLogo)
            }
        }
    }

    class HeaderViewHolder(private val binding: TeamInfoHeaderItemBinding) : TeamInfoViewHolder(binding) {

        fun bind(header: HeaderItem) {
            binding.headerTitle.text = header.text
            when(header.state) {
                Success::class.java -> {
                    binding.progressBar.root.visibility = View.INVISIBLE
                    binding.error.visibility = View.INVISIBLE
                }
                Error::class.java -> {
                    binding.progressBar.root.visibility = View.INVISIBLE
                    binding.error.visibility = View.VISIBLE
                }
                Loading::class.java -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                    binding.error.visibility = View.INVISIBLE
                }
            }
        }
    }

}