package com.anti_toxic.dota.databases.di

import android.content.Context
import androidx.room.Room
import com.anti_toxic.dota.core_api.di.ApplicationContext
import com.anti_toxic.dota.databases.teams.TeamsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTeamsDatabase(@ApplicationContext context: Context): TeamsDatabase {
        return Room.databaseBuilder(
            context,
            TeamsDatabase::class.java,
            TeamsDatabase.NAME
        ).build()
    }
}