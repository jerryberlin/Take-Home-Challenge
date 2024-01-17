package com.takehomechallenge.jerryberlin.core.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.takehomechallenge.jerryberlin.core.data.CharacterRemoteMediator
import com.takehomechallenge.jerryberlin.core.data.Resource
import com.takehomechallenge.jerryberlin.core.data.local.entity.CharacterEntity
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import com.takehomechallenge.jerryberlin.core.data.remote.network.ApiService
import com.takehomechallenge.jerryberlin.core.model.Response
import com.takehomechallenge.jerryberlin.core.model.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDatabase: CharacterDatabase,
    private val apiService: ApiService,
) {
    fun getCharacters(): LiveData<PagingData<CharacterEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = CharacterRemoteMediator(characterDatabase, apiService),
            pagingSourceFactory = {
                characterDatabase.characterDao().getAllCharacters()
            }
        ).flow.asLiveData()
    }

    fun getDetailCharacter(id: Int): Flow<Resource<ResultsItem>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = apiService.getDetailCharacter(id)
                emit(
                    Resource.Success(
                        data = result
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchCharacter(name: String): Flow<Resource<Response>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = apiService.searchCharacter(name)
                emit(
                    Resource.Success(
                        data = result
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertCharacterToFavorite(favoriteEntity: FavoriteEntity) =
        characterDatabase.favoriteDao().insertCharacterToFavorite(favoriteEntity)

    fun getCharacterFromFavorite(): Flow<List<FavoriteEntity>> =
        characterDatabase.favoriteDao().getCharacterFromFavorite()

    fun getCharacterByIdFromFavorite(characterId: Int): Flow<FavoriteEntity> =
        characterDatabase.favoriteDao().getCharacterByIdFromFavorite(characterId)

    suspend fun deleteCharacterFromFavorite(favoriteEntity: FavoriteEntity) =
        characterDatabase.favoriteDao().deleteCharacterFromFavorite(favoriteEntity)

    fun checkId(id: Int) =
        characterDatabase.favoriteDao().checkId(id)

}