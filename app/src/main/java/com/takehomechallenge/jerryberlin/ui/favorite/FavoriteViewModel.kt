package com.takehomechallenge.jerryberlin.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.takehomechallenge.jerryberlin.core.data.local.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val character =
        characterRepository.getCharacterFromFavorite().asLiveData()

}