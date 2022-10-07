package com.anti_toxic.dota.teams.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.anti_toxic.dota.core_api.OneShotEvent
import com.anti_toxic.dota.core_api.R
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.teams.list.TeamsListViewModel.Event.RefreshEnded
import com.anti_toxic.dota.teams.list.TeamsListViewModel.Event.ShowSnackbar
import com.anti_toxic.dota.teams.list.data_source.Team
import com.anti_toxic.dota.teams.list.data_source.TeamsListRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TeamsListViewModel(
    private val repository: TeamsListRepository,
    private val resources: Resources
) : ViewModel(), OnRefreshListener {

    sealed class Event {
        class ShowSnackbar(val text: String) : Event()
        object RefreshEnded : Event()
    }

    private val _eventChannel = Channel<OneShotEvent<Event>>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _teamsListStateFlow: MutableStateFlow<Resource<List<Team>>> = MutableStateFlow(Empty())
    val teamsListStateFlow: StateFlow<Resource<List<Team>>> = _teamsListStateFlow

    private var getTeamsJob: Job? = null

    fun getTeams(isRefresh: Boolean = false) {
        getTeamsJob?.cancel()
        getTeamsJob = viewModelScope.launch {
            repository.getTeamsList(isRefresh)
                .onCompletion {
                    if (isRefresh) {
                        _eventChannel.send(OneShotEvent(RefreshEnded))
                    }
                }
                .collect { response ->
                    when (response) {
                        is Error -> {
                            if (_teamsListStateFlow.value is Success) {
                                _eventChannel.send(
                                    OneShotEvent(
                                        ShowSnackbar(resources.getString(R.string.error_loading_data))
                                    )
                                )
                            } else {
                                _teamsListStateFlow.emit(response)
                            }
                        }
                        is Loading -> {
                            if (_teamsListStateFlow.value !is Success) {
                                _teamsListStateFlow.emit(response)
                            }
                        }
                        else -> {
                            _teamsListStateFlow.emit(response)
                        }
                    }
                }
        }
    }

    override fun onRefresh() {
        getTeams(true)
    }
}

