package com.anti_toxic.dota.teams.info

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
import com.anti_toxic.dota.teams.databinding.FragmentTeamInfoBinding
import com.anti_toxic.dota.teams.info.TeamInfoViewModel.Event
import com.anti_toxic.dota.teams.info.recycler.TeamInfoAdapter
import com.anti_toxic.dota.ui.R
import com.anti_toxic.dota.ui.ViewBindingFragment
import timber.log.Timber
import javax.inject.Inject


class TeamInfoFragment :
    ViewBindingFragment<FragmentTeamInfoBinding>(FragmentTeamInfoBinding::inflate) {

    @Inject
    lateinit var viewModelFactoryFactory: TeamInfoViewModelFactoryFactory

    private val adapter = TeamInfoAdapter()

    private lateinit var viewModel: TeamInfoViewModel

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

        val team = arguments?.let {
            TeamInfoFragmentArgs.Companion.fromBundle(it).team
        } ?: run {
            Timber.e(IllegalArgumentException("Team argument must not be null"))
            findNavController().popBackStack()
            return@onViewCreated
        }

        viewModel = ViewModelProvider(this, viewModelFactoryFactory.create(team))[TeamInfoViewModel::class.java]

        initViews()

        lifecycleScope.launchWhenStarted {
            collectTeamsListResponse()
        }

        lifecycleScope.launchWhenStarted {
            collectOneShotEvents()
        }
    }

    private fun initViews() {
        viewBinding.error.retryButton.setOnClickListener {
            viewModel.getInfo()
        }
        viewBinding.swipeRefreshLayout.setColorSchemeColors(
            resources.getColor(R.color.primaryColor, context?.theme)
        )
        viewBinding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
            resources.getColor(R.color.secondaryColor, context?.theme)
        )
        viewBinding.swipeRefreshLayout.setOnRefreshListener(viewModel)

        val recyclerView = viewBinding.teamInfoRecycler

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
        viewModel.teamInfoItems.collect { result ->
            Timber.d("Teams info item observing result $result")
            when (result) {
                is Success -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.teamInfoRecycler.visibility = View.VISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                    adapter.items = result.data
                }
                is Loading -> {
                    viewBinding.progressBar.root.visibility = View.VISIBLE
                    viewBinding.teamInfoRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.INVISIBLE
                }
                is Error -> {
                    viewBinding.progressBar.root.visibility = View.INVISIBLE
                    viewBinding.teamInfoRecycler.visibility = View.INVISIBLE
                    viewBinding.error.root.visibility = View.VISIBLE
                }
                is Empty -> {
                    viewModel.getInfo()
                }
            }
        }
    }

    private suspend fun collectOneShotEvents() {
        viewModel.eventFlow.collect {
            it.getValue()?.let { event ->
                when (event) {
                    is Event.RefreshEnded -> viewBinding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
}