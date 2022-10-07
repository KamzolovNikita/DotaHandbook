package com.anti_toxic.dota.items.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.items.databinding.ItemsListItemBinding
import com.anti_toxic.dota.items.list.data_source.Item

class ItemsListAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ItemItemViewHolder>() {

    var items: List<Item> = emptyList()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemItemViewHolder {
        val binding = ItemsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount() = items.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ItemClickListener(val clickListener: (team: Item) -> Unit) {
    fun onClick(item: Item) = clickListener(item)
}