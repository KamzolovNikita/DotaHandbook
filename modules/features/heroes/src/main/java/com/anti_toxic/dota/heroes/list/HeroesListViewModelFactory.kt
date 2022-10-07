package com.anti_toxic.dota.heroes.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anti_toxic.dota.heroes.list.data_source.HeroesListRepository
import javax.inject.Inject

class HeroesListViewModelFactory @Inject constructor(
    private val heroesListRepository: HeroesListRepository,
    private val heroesListFilter: HeroesListFilter,
    private val resources: Resources
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeroesListViewModel::class.java)) {
            return HeroesListViewModel(heroesListRepository, heroesListFilter, resources) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}