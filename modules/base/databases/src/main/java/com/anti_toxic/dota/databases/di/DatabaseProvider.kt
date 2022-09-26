package com.anti_toxic.dota.databases.di

import com.anti_toxic.dota.databases.teams.TeamsDatabase

interface DatabaseProvider {

    fun provideTeamsListDatabase(): TeamsDatabase
}