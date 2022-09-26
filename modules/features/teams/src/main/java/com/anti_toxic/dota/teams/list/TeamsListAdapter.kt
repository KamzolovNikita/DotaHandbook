package com.anti_toxic.dota.teams.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.open_dota_api.dto.Team
import com.anti_toxic.dota.teams.R.string
import com.anti_toxic.dota.teams.databinding.TeamListItemBinding
import com.anti_toxic.dota.teams.list.TeamsListAdapter.TeamItemViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import javax.inject.Inject

class TeamsListAdapter @Inject constructor() : RecyclerView.Adapter<TeamItemViewHolder>() {

    var dataset: List<Team> = emptyList()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamItemViewHolder {
        val binding = TeamListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataset.size


    class TeamItemViewHolder(private val viewBinding: TeamListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(team: Team) {
            val resources = viewBinding.root.context.resources
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
                .into(viewBinding.teamLogo)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem.teamId == newItem.teamId
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem == newItem
            }
        }
    }
}