package com.anti_toxic.dota.heroes.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.heroes.DaggerHeroesComponent
import com.anti_toxic.dota.heroes.R
import com.anti_toxic.dota.heroes.databinding.FragmentHeroesListBinding
import com.anti_toxic.dota.heroes.filter_dialog.FilterHeroesDialogFragment
import com.anti_toxic.dota.heroes.filter_dialog.FilterHeroesDialogFragment.FiltersChooseDialogListener
import com.anti_toxic.dota.heroes.filter_dialog.HeroesFilters
import com.anti_toxic.dota.heroes.list.HeroesListViewModel.Event
import com.anti_toxic.dota.heroes.list.recycler.HeroesListAdapter
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.ui.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class HeroesListFragment
    : ViewBindingFragment<FragmentHeroesListBinding>(FragmentHeroesListBinding::inflate), FiltersChooseDialogListener {

    @Inject
    lateinit var viewModelFactory: HeroesListViewModelFactory

    private val adapter = HeroesListAdapter()

    private lateinit var viewModel: HeroesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = activity as ComponentProvider

        DaggerHeroesComponent.factory().create(
            componentProvider.provide(CoreProvider::class.java),
            componentProvider.provide(OpenDotaApiServiceProvider::class.java),
            componentProvider.provide(DatabaseProvider::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[HeroesListViewModel::class.java]

        if (viewModel.filteredHeroesStateFlow.value is Empty) {
            viewModel.getHeroes()
        }

        initViews()

        lifecycleScope.launchWhenStarted {
            collectHeroesResponse()
        }

        lifecycleScope.launchWhenStarted {
            collectIsFiltered()
        }

        lifecycleScope.launchWhenStarted {
            collectOneShotEvents()
        }
    }

    private fun initViews() {
        viewBinding.error.retryButton.setOnClickListener {
            viewModel.getHeroes()
        }

        val recyclerView = viewBinding.heroesRecycler
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
    }

    private suspend fun collectHeroesResponse() {
        viewModel.filteredHeroesStateFlow.collect { result ->
            Timber.d("Heroes list observing result $result")
            when (result) {
                is Success -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.heroesRecycler.visibility = View.VISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                    adapter.items = result.data
                }
                is Loading -> {
                    viewBinding.progressBar.root.visibility = View.VISIBLE
                    viewBinding.heroesRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                }
                is Error -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.heroesRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.VISIBLE
                }
                is Empty -> {}
            }
        }
    }

    private suspend fun collectIsFiltered() {
        viewModel.isFiltered.collect { isFiltered ->
            if (isFiltered) {
                viewBinding.filterFab.setOnClickListener {
                    viewModel.applyFilters(null)
                }
                viewBinding.filterFab.setImageDrawable(resources.getDrawable(R.drawable.ic_xmark, context?.theme))
            } else {
                viewBinding.filterFab.setOnClickListener {
                    val dialog = FilterHeroesDialogFragment()
                    dialog.setOnFiltersChosenListener(this)
                    dialog.show(childFragmentManager, "asd")
                }
                viewBinding.filterFab.setImageDrawable(resources.getDrawable(R.drawable.ic_search, context?.theme))
            }
        }
    }

    private suspend fun collectOneShotEvents() {
        viewModel.eventFlow.collect {
            it.getValue()?.let { event ->
                when (event) {
                    is Event.ShowSnackbar -> Snackbar.make(viewBinding.root, event.text, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onFiltersChosen(heroesFilters: HeroesFilters) {
        viewModel.applyFilters(heroesFilters)
    }
}