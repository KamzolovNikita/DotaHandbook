package com.anti_toxic.dota.items.list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.items.databinding.ItemsListItemBinding
import com.anti_toxic.dota.items.list.data_source.Item
import com.anti_toxic.dota.ui.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ItemItemViewHolder(private val viewBinding: ItemsListItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Item, itemClickListener: ItemClickListener) {
        val resources = viewBinding.root.resources

        viewBinding.root.setOnClickListener {
            itemClickListener.onClick(item)
        }

        viewBinding.name.text = item.name

        val dotaIcon = resources.getDrawable(R.drawable.ic_dota, viewBinding.root.context.theme)
        dotaIcon.setTint(resources.getColor(R.color.secondaryDarkColor, viewBinding.root.context.theme))
        Glide.with(viewBinding.image)
            .load(item.imageUrl)
            .placeholder(dotaIcon)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(viewBinding.image)
    }
}