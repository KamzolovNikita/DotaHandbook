package com.anti_toxic.dota.databases.di

import com.anti_toxic.dota.databases.heroes.HeroesDatabase
import com.anti_toxic.dota.databases.items.ItemsDatabase
import com.anti_toxic.dota.databases.teams.TeamsDatabase

interface DatabaseProvider {

    fun provideTeamsListDatabase(): TeamsDatabase

    fun provideHeroesListDatabase(): HeroesDatabase

    fun provideItemsListDatabase(): ItemsDatabase
}