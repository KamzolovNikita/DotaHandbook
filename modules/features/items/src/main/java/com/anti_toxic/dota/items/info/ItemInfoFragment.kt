package com.anti_toxic.dota.items.info

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.items.DaggerItemsComponent
import com.anti_toxic.dota.items.databinding.FragmentItemInfoBinding
import com.anti_toxic.dota.items.list.data_source.Item
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.ui.R
import com.anti_toxic.dota.ui.ViewBindingFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import timber.log.Timber
import javax.inject.Inject

class ItemInfoFragment
    : ViewBindingFragment<FragmentItemInfoBinding>(FragmentItemInfoBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ItemInfoViewModelFactory

    lateinit var viewModel: ItemInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = activity as ComponentProvider

        DaggerItemsComponent.factory().create(
            componentProvider.provide(CoreProvider::class.java),
            componentProvider.provide(OpenDotaApiServiceProvider::class.java),
            componentProvider.provide(DatabaseProvider::class.java)
        ).inject(this)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[ItemInfoViewModel::class.java]

        val item = arguments?.let {
            val itemInfo = ItemInfoFragmentArgs.Companion.fromBundle(it).item
            viewModel.itemInfo = itemInfo
            itemInfo
        } ?: viewModel.itemInfo

        if (item == null) {
            Timber.e(IllegalArgumentException("Item argument must not be null"))
            findNavController().popBackStack()
            return
        }

        applyItemInfoToViews(item)
        viewBinding.root.requestFocus()
    }

    private fun applyItemInfoToViews(item: Item) {
        if (item.cooldown != null) {
            viewBinding.cooldownGroup.visibility = View.VISIBLE
            viewBinding.cooldown.text = item.cooldown.toString()
        }

        viewBinding.name.text = item.name
        viewBinding.price.text = item.cost.toString()
        viewBinding.lore.text = item.lore
        viewBinding.hints.text = item.hints.mapIndexed { index, hint ->
            "${index + 1}. $hint"
        }.joinToString(separator = "\n\n")

        val dotaIcon = resources.getDrawable(R.drawable.ic_dota, viewBinding.root.context.theme)
        dotaIcon.setTint(resources.getColor(R.color.secondaryDarkColor, viewBinding.root.context.theme))

        Glide.with(viewBinding.image)
            .load(item.imageUrl)
            .placeholder(dotaIcon)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(viewBinding.image)
    }
}