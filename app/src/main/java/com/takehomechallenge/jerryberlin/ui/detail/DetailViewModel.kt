package com.takehomechallenge.jerryberlin.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.takehomechallenge.jerryberlin.core.data.local.CharacterRepository
import com.takehomechallenge.jerryberlin.core.data.local.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    fun getDetailCharacter(id: Int) =
        characterRepository.getDetailCharacter(id).asLiveData()

    fun checkId(id: Int) = characterRepository.checkId(id).asLiveData()

    suspend fun insertCharacterToFavorite(favoriteEntity: FavoriteEntity) =
        characterRepository.insertCharacterToFavorite(favoriteEntity)

    suspend fun deleteCharacterFromFavorite(favoriteEntity: FavoriteEntity) =
        characterRepository.deleteCharacterFromFavorite(favoriteEntity)

    fun getCharacterByIdFromFavorite(characterId: Int) =
        characterRepository.getCharacterByIdFromFavorite(characterId).asLiveData()
}