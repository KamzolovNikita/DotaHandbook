package com.anti_toxic.dota.teams.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.anti_toxic.dota.core_api.OneShotEvent
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.teams.info.TeamInfoViewModel.Event.RefreshEnded
import com.anti_toxic.dota.teams.info.recycler.TeamInfoViewItem
import com.anti_toxic.dota.teams.list.data_source.Team
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TeamInfoViewModel(
    private val interactor: TeamInfoInteractor,
    private val team: Team
) : ViewModel(), OnRefreshListener {

    sealed class Event {
        object RefreshEnded : Event()
    }

    private val _eventChannel = Channel<OneShotEvent<Event>>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _teamInfoItems: MutableStateFlow<Resource<List<TeamInfoViewItem>>> = MutableStateFlow(Empty())
    var teamInfoItems: StateFlow<Resource<List<TeamInfoViewItem>>> = _teamInfoItems

    private var fetchInfoJob: Job? = null

    fun getInfo(isRefresh: Boolean = false) {
        fetchInfoJob?.cancel()
        fetchInfoJob = viewModelScope.launch {
            interactor.getTeamInfoViewItemsFlow(team, isRefresh)
                .onCompletion {
                    if (isRefresh) {
                        _eventChannel.send(OneShotEvent(RefreshEnded))
                    }
                }
                .collect { response ->
                    when (response) {
                        is Error,
                        is Loading -> {
                            if (_teamInfoItems.value !is Success) {
                                _teamInfoItems.emit(response)
                            }
                        }
                        else -> {
                            _teamInfoItems.emit(response)
                        }
                    }
                }
        }
    }

    override fun onRefresh() {
        getInfo(true)
    }
}

