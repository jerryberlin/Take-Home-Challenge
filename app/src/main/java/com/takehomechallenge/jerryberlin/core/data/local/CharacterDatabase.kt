package com.takehomechallenge.jerryberlin.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.takehomechallenge.jerryberlin.core.data.local.dao.CharacterDao
import com.takehomechallenge.jerryberlin.core.data.local.dao.FavoriteDao
import com.takehomechallenge.jerryberlin.core.data.local.dao.RemoteKeysDao
import com.takehomechallenge.jerryberlin.core.data.local.entity.CharacterEntity
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity

@Database(
    entities = [CharacterEntity::class, FavoriteEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false,
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}