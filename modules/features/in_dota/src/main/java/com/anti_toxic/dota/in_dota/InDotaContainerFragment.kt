package com.anti_toxic.dota.in_dota

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.anti_toxic.dota.in_dota.databinding.FragmentInDotaContainerBinding
import com.anti_toxic.dota.ui.ViewBindingFragment

class InDotaContainerFragment :
    ViewBindingFragment<FragmentInDotaContainerBinding>(FragmentInDotaContainerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonToggleGroup = viewBinding.buttonToggleGroup
        openFragment(ITEMS_FRAGMENT_TAG) { ItemsContainerFragment() }
        buttonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button_heroes -> {
                        openFragment(HEROES_FRAGMENT_TAG) { HeroesContainerFragment() }
                    }
                    R.id.button_items -> {
                        openFragment(ITEMS_FRAGMENT_TAG) { ItemsContainerFragment() }
                    }
                }
            }
        }
    }

    private fun openFragment(tag: String, initFragment: () -> Fragment) {
        childFragmentManager.findFragmentByTag(tag)?.let {
            childFragmentManager.beginTransaction().show(it).commit()
        } ?: run {
            childFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                initFragment(),
                tag
            ).commit()
        }
        ALL_FRAGMENTS
            .filter { it != tag }
            .forEach { tagToHide ->
                childFragmentManager.findFragmentByTag(tagToHide)?.let { fragment ->
                    childFragmentManager.beginTransaction().hide(fragment).commit()
                }
            }
    }

    companion object {
        private const val HEROES_FRAGMENT_TAG = "Heroes"
        private const val ITEMS_FRAGMENT_TAG = "Items"

        private val ALL_FRAGMENTS = listOf(HEROES_FRAGMENT_TAG, ITEMS_FRAGMENT_TAG)
    }
}