package com.anti_toxic.dota.databases.di

import android.content.Context
import androidx.room.Room
import com.anti_toxic.dota.core_api.di.ApplicationContext
import com.anti_toxic.dota.databases.heroes.HeroesDatabase
import com.anti_toxic.dota.databases.items.ItemsDatabase
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

    @Singleton
    @Provides
    fun provideHeroesDatabase(@ApplicationContext context: Context): HeroesDatabase {
        return Room.databaseBuilder(
            context,
            HeroesDatabase::class.java,
            HeroesDatabase.NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideItemsDatabase(@ApplicationContext context: Context): ItemsDatabase {
        return Room.databaseBuilder(
            context,
            ItemsDatabase::class.java,
            ItemsDatabase.NAME
        ).build()
    }
}