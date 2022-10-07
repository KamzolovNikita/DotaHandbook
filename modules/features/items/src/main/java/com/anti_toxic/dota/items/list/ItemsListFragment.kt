package com.anti_toxic.dota.items.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.items.DaggerItemsComponent
import com.anti_toxic.dota.items.databinding.FragmentItemsListBinding
import com.anti_toxic.dota.items.list.ItemsListViewModel.Event
import com.anti_toxic.dota.items.list.data_source.Item
import com.anti_toxic.dota.items.list.recycler.ItemClickListener
import com.anti_toxic.dota.items.list.recycler.ItemsListAdapter
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.ui.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class ItemsListFragment
    : ViewBindingFragment<FragmentItemsListBinding>(FragmentItemsListBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: ItemsListViewModelFactory

    lateinit var viewModel: ItemsListViewModel

    private val adapter = ItemsListAdapter(
        ItemClickListener { navigateItemInfoFragment(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = activity as ComponentProvider


        DaggerItemsComponent.factory().create(
            componentProvider.provide(CoreProvider::class.java),
            componentProvider.provide(OpenDotaApiServiceProvider::class.java),
            componentProvider.provide(DatabaseProvider::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[ItemsListViewModel::class.java]
        if (viewModel.itemsStateFlow.value is Empty) {
            viewModel.getItems()
        }
        initViews()

        lifecycleScope.launchWhenStarted {
            collectItemsResponse()
        }

        lifecycleScope.launchWhenStarted {
            collectOneShotEvents()
        }
    }

    private fun initViews() {
        viewBinding.error.retryButton.setOnClickListener {
            viewModel.getItems()
        }

        val recyclerView = viewBinding.heroesRecycler
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false)
    }

    private suspend fun collectItemsResponse() {
        viewModel.itemsStateFlow.collect { result ->
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

    private suspend fun collectOneShotEvents() {
        viewModel.eventFlow.collect {
            it.getValue()?.let { event ->
                when (event) {
                    is Event.ShowSnackbar -> Snackbar.make(viewBinding.root, event.text, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateItemInfoFragment(item: Item) {
        findNavController().navigate(
            ItemsListFragmentDirections.actionItemsListFragmentToItemInfoFragment(item)
        )
    }
}