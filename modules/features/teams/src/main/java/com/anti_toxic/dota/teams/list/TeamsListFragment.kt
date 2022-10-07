package com.anti_toxic.dota.teams.list

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.databases.di.DatabaseProvider
import com.anti_toxic.dota.open_dota_api.OpenDotaApiServiceProvider
import com.anti_toxic.dota.teams.DaggerTeamsComponent
import com.anti_toxic.dota.teams.databinding.FragmentTeamsListBinding
import com.anti_toxic.dota.teams.list.TeamsListViewModel.Event
import com.anti_toxic.dota.teams.list.data_source.Team
import com.anti_toxic.dota.teams.list.recycler.TeamClickListener
import com.anti_toxic.dota.teams.list.recycler.TeamsListAdapter
import com.anti_toxic.dota.ui.R
import com.anti_toxic.dota.ui.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class TeamsListFragment
    : ViewBindingFragment<FragmentTeamsListBinding>(FragmentTeamsListBinding::inflate) {

    @Inject
    lateinit var viewModelFactory: TeamsListViewModelFactory

    private val adapter = TeamsListAdapter(
        TeamClickListener(this::navigateTeamInfoFragment)
    )

    private lateinit var viewModel: TeamsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = activity as ComponentProvider

        DaggerTeamsComponent.factory().create(
            componentProvider.provide(CoreProvider::class.java),
            componentProvider.provide(OpenDotaApiServiceProvider::class.java),
            componentProvider.provide(DatabaseProvider::class.java)
        ).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[TeamsListViewModel::class.java]

        initViews()

        lifecycleScope.launchWhenStarted {
            collectOneShotEvents()
        }
        lifecycleScope.launchWhenStarted {
            collectTeamsListResponse()
        }
    }

    private fun initViews() {
        viewBinding.error.retryButton.setOnClickListener {
            viewModel.getTeams()
        }
        viewBinding.swipeRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.primaryColor, context?.theme)
        )
        viewBinding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            resources.getColor(R.color.secondaryColor, context?.theme)
        )
        viewBinding.swipeRefreshLayout.setOnRefreshListener(viewModel)

        val recyclerView = viewBinding.teamsRecycler
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayout.VERTICAL
        )
        dividerItemDecoration.setDrawable(resources.getDrawable(
            R.drawable.recycler_divider,
            context?.theme
        ))
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private suspend fun collectTeamsListResponse() {
        viewModel.teamsListStateFlow.collect { result ->
            Timber.d("Teams list observing result $result")
            when (result) {
                is Success -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.teamsRecycler.visibility = View.VISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                    adapter.dataset = result.data
                }
                is Loading -> {
                    viewBinding.progressBar.root.visibility = View.VISIBLE
                    viewBinding.teamsRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                }
                is Error -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.teamsRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.VISIBLE
                }
                is Empty -> {
                    viewModel.getTeams()
                }
            }
        }
    }

    private suspend fun collectOneShotEvents() {
        viewModel.eventFlow.collect {
            it.getValue()?.let { event ->
                when (event) {
                    is Event.RefreshEnded -> viewBinding.swipeRefreshLayout.isRefreshing = false
                    is Event.ShowSnackbar -> Snackbar.make(viewBinding.root, event.text, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateTeamInfoFragment(team: Team) {
        findNavController().navigate(
            TeamsListFragmentDirections.actionTeamsFragmentToTeamInfoFragment(team)
        )
    }
}