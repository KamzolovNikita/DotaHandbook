package com.anti_toxic.dota.teams.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anti_toxic.dota.core_api.di.ApplicationContext
import com.anti_toxic.dota.teams.list.data_source.TeamsListRepository
import javax.inject.Inject

class TeamsListViewModelFactory @Inject constructor(
    private val teamsListInteractor: TeamsListRepository,
    @ApplicationContext private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsListViewModel::class.java)) {
            return TeamsListViewModel(teamsListInteractor, context) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}