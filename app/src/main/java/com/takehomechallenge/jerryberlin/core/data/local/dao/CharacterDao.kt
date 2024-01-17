package com.takehomechallenge.jerryberlin.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.takehomechallenge.jerryberlin.core.data.local.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(user: CharacterEntity)

    @Query("SELECT * FROM character")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM character")
    suspend fun deleteAll()
}