package com.anti_toxic.dota.heroes.list

import com.anti_toxic.dota.core_api.Resource
import com.anti_toxic.dota.core_api.Resource.Success
import com.anti_toxic.dota.heroes.filter_dialog.HeroesFilters
import com.anti_toxic.dota.heroes.list.data_source.Hero
import javax.inject.Inject

class HeroesListFilter @Inject constructor() {

    fun filter(heroes: Resource<List<Hero>>, filters: HeroesFilters?): Resource<List<Hero>> {
        if (heroes !is Success || filters == null) {
            return heroes
        }
        return Success(heroes.data
            .filter { hero ->
                var isCorrect = true
                if (filters.attackType != null) {
                    isCorrect = hero.attackType == filters.attackType
                }
                if (filters.role != null) {
                    isCorrect = hero.roles.contains(filters.role)
                }
                if (filters.primaryAttribute != null) {
                    isCorrect = hero.primaryAttribute == filters.primaryAttribute
                }
                isCorrect
            })
    }
}