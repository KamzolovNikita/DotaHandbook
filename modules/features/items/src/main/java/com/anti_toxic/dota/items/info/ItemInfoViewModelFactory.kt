package com.anti_toxic.dota.items.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ItemInfoViewModelFactory @Inject constructor(
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemInfoViewModel::class.java)) {
            return ItemInfoViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}