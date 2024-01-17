package com.takehomechallenge.jerryberlin.core.di

import android.content.Context
import androidx.room.Room
import com.takehomechallenge.jerryberlin.core.data.local.CharacterDatabase
import com.takehomechallenge.jerryberlin.core.data.local.dao.CharacterDao
import com.takehomechallenge.jerryberlin.core.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CharacterDatabase =
        Room.databaseBuilder(
            context,
            CharacterDatabase::class.java, "Character.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideRemoteKeysDao(database: CharacterDatabase): RemoteKeysDao = database.remoteKeysDao()

    @Provides
    fun provideUserDao(database: CharacterDatabase): CharacterDao = database.characterDao()
}