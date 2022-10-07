package com.anti_toxic.dota.items.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anti_toxic.dota.core_api.OneShotEvent
import com.anti_toxic.dota.core_api.R.string
import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Empty
import com.anti_toxic.dota.core_api.Resource.Error
import com.anti_toxic.dota.core_api.Resource.Loading
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.items.list.ItemsListViewModel.Event.ShowSnackbar
import com.anti_toxic.dota.items.list.data_source.Item
import com.anti_toxic.dota.items.list.data_source.ItemsListRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ItemsListViewModel(
    private val itemsListRepository: ItemsListRepository,
    private val resources: Resources
) : ViewModel() {

    private val _itemsStateFlow = MutableStateFlow<Resource<List<Item>>>(Empty())
    val itemsStateFlow: StateFlow<Resource<List<Item>>>
        get() = _itemsStateFlow

    private val _eventChannel = Channel<OneShotEvent<Event>>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    sealed class Event {
        class ShowSnackbar(val text: String) : Event()
    }

    private var fetchItemsJob: Job? = null

    fun getItems() {
        fetchItemsJob?.cancel()
        fetchItemsJob = viewModelScope.launch {
            itemsListRepository.getItems()
                .collect { response ->
                    when (response) {
                        is Error -> {
                            if (_itemsStateFlow.value is Success) {
                                _eventChannel.send(
                                    OneShotEvent(
                                        ShowSnackbar(resources.getString(string.error_loading_data))
                                    )
                                )
                            } else {
                                _itemsStateFlow.emit(response)
                            }
                        }
                        is Loading -> {
                            if (_itemsStateFlow.value !is Success) {
                                _itemsStateFlow.emit(response)
                            }
                        }
                        else -> {
                            _itemsStateFlow.emit(response)
                        }
                    }
                }
        }
    }
}