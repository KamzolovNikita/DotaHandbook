package com.anti_toxic.dota.heroes.list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.anti_toxic.dota.heroes.databinding.HeroesListItemBinding
import com.anti_toxic.dota.heroes.list.data_source.Hero
import com.anti_toxic.dota.ui.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class HeroItemViewHolder(private val viewBinding: HeroesListItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(hero: Hero) {
        val resources = viewBinding.root.resources

        viewBinding.name.text = hero.name

        val dotaIcon = resources.getDrawable(R.drawable.ic_dota, viewBinding.root.context.theme)
        dotaIcon.setTint(resources.getColor(R.color.secondaryDarkColor, viewBinding.root.context.theme))
        Glide.with(viewBinding.image)
            .load(hero.imageUrl)
            .placeholder(dotaIcon)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(viewBinding.image)
    }
}