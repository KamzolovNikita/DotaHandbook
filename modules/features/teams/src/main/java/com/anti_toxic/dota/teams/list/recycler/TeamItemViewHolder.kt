package com.anti_toxic.dota.teams.list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.teams.R
import com.anti_toxic.dota.teams.R.string
import com.anti_toxic.dota.teams.databinding.TeamListItemBinding
import com.anti_toxic.dota.teams.list.data_source.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class TeamItemViewHolder(private val viewBinding: TeamListItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(team: Team, clickListener: TeamClickListener) {
        val resources = viewBinding.root.context.resources
        viewBinding.root.setOnClickListener {
            clickListener.onClick(team)
        }
        viewBinding.teamName.text = team.name
        viewBinding.teamRanking.text = String.format(
            resources.getString(string.team_ranking_placeholder),
            adapterPosition + 1
        )
        viewBinding.teamRating.text = String.format(
            resources.getString(string.team_rating_placeholder),
            team.rating.toString()
        )

        Glide.with(viewBinding.teamLogo)
            .load(team.logoUrl)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(resources.getDrawable(R.drawable.ic_team, viewBinding.root.context.theme))
            .into(viewBinding.teamLogo)
    }
}