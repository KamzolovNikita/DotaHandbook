package com.anti_toxic.dota.items.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anti_toxic.dota.items.list.data_source.ItemsListRepository
import javax.inject.Inject


class ItemsListViewModelFactory @Inject constructor(
    private val itemsLitRepository: ItemsListRepository,
    private val resources: Resources
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsListViewModel::class.java)) {
            return ItemsListViewModel(itemsLitRepository, resources) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}