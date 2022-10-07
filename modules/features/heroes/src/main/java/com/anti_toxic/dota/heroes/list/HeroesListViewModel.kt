package com.anti_toxic.dota.heroes.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anti_toxic.dota.core_api.OneShotEvent
import com.anti_toxic.dota.core_api.R
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.heroes.filter_dialog.HeroesFilters
import com.anti_toxic.dota.heroes.list.HeroesListViewModel.Event.ShowSnackbar
import com.anti_toxic.dota.heroes.list.data_source.Hero
import com.anti_toxic.dota.heroes.list.data_source.HeroesListRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HeroesListViewModel(
    private val repository: HeroesListRepository,
    private val heroesListFilter: HeroesListFilter,
    private val resources: Resources
) : ViewModel() {

    private val _isFiltered = MutableStateFlow(false)
    val isFiltered: StateFlow<Boolean>
        get() = _isFiltered

    private val _eventChannel = Channel<OneShotEvent<Event>>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val appliedFilters = MutableStateFlow<HeroesFilters?>(null)
    private val heroesStateFlow = MutableStateFlow<Resource<List<Hero>>>(Empty())

    private val _filteredHeroesStateFlow = MutableStateFlow<Resource<List<Hero>>>(Empty())
    val filteredHeroesStateFlow = _filteredHeroesStateFlow.asStateFlow()

    private var fetchHeroesJob: Job? = null

    sealed class Event {
        class ShowSnackbar(val text: String) : Event()
    }

    init {
        viewModelScope.launch {
            combine(
                heroesStateFlow,
                appliedFilters,
                heroesListFilter::filter
            ).collect {
                _filteredHeroesStateFlow.emit(it)
            }
        }
    }

    fun getHeroes() {
        fetchHeroesJob?.cancel()
        fetchHeroesJob = viewModelScope.launch {
            repository.getHeroes()
                .collect { response ->
                    when (response) {
                        is Error -> {
                            if (heroesStateFlow.value is Success) {
                                _eventChannel.send(
                                    OneShotEvent(
                                        ShowSnackbar(resources.getString(R.string.error_loading_data))
                                    )
                                )
                            } else {
                                heroesStateFlow.emit(response)
                            }
                        }
                        is Loading -> {
                            if (heroesStateFlow.value !is Success) {
                                heroesStateFlow.emit(response)
                            }
                        }
                        else -> {
                            heroesStateFlow.emit(response)
                        }
                    }
                }
        }
    }

    fun applyFilters(filters: HeroesFilters?) {
        viewModelScope.launch {
            appliedFilters.emit(filters)
            _isFiltered.emit(
                filters?.role != null ||
                    filters?.primaryAttribute != null ||
                    filters?.attackType != null
            )
        }

    }
}