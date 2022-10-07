package com.anti_toxic.dota.teams.list

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anti_toxic.dota.teams.list.data_source.TeamsListRepository
import javax.inject.Inject

class TeamsListViewModelFactory @Inject constructor(
    private val teamsListRepository: TeamsListRepository,
    private val resources: Resources
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsListViewModel::class.java)) {
            return TeamsListViewModel(teamsListRepository, resources) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}