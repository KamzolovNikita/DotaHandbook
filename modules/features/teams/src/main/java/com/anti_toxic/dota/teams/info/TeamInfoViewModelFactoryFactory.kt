package com.anti_toxic.dota.teams.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anti_toxic.dota.teams.list.data_source.Team
import javax.inject.Inject

class TeamInfoViewModelFactoryFactory @Inject constructor(
    private val teamInfoInteractor: TeamInfoInteractor,
) {

    fun create(team: Team): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TeamInfoViewModel::class.java)) {
                    return TeamInfoViewModel(
                        teamInfoInteractor,
                        team
                    ) as T
                } else {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}