package com.anti_toxic.dota.teams.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.teams.databinding.TeamListItemBinding
import com.anti_toxic.dota.teams.list.data_source.Team

class TeamsListAdapter(
    private val clickListener: TeamClickListener
) : RecyclerView.Adapter<TeamItemViewHolder>() {

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
        holder.bind(item, clickListener)
    }

    override fun getItemCount() = dataset.size

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

class TeamClickListener(val clickListener: (team: Team) -> Unit) {
    fun onClick(team: Team) = clickListener(team)
}