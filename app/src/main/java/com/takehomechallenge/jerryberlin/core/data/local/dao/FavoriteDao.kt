package com.takehomechallenge.jerryberlin.core.data.local.dao

import androidx.room.*
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterToFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getCharacterFromFavorite(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE characterId =:characterId")
    fun getCharacterByIdFromFavorite(characterId: Int): Flow<FavoriteEntity>

    @Delete
    suspend fun deleteCharacterFromFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT characterId FROM favorite WHERE characterId = :id LIMIT 1")
    fun checkId(id: Int): Flow<Int>


}