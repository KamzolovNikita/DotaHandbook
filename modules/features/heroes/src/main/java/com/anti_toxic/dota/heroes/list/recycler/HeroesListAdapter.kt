package com.anti_toxic.dota.heroes.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.heroes.databinding.HeroesListItemBinding
import com.anti_toxic.dota.heroes.list.data_source.Hero

class HeroesListAdapter() : RecyclerView.Adapter<HeroItemViewHolder>() {

    var items: List<Hero> = emptyList()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroItemViewHolder {
        val binding = HeroesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hero>() {
            override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
                return oldItem == newItem
            }
        }
    }
}