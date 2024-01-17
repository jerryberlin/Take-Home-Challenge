package com.takehomechallenge.jerryberlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.takehomechallenge.jerryberlin.core.data.local.CharacterRepository
import com.takehomechallenge.jerryberlin.core.data.local.entity.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    val characters: LiveData<PagingData<CharacterEntity>> =
        characterRepository.getCharacters().cachedIn(viewModelScope)
}