package com.takehomechallenge.jerryberlin.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.takehomechallenge.jerryberlin.core.data.local.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    fun searchCharacter(name: String) =
        characterRepository.searchCharacter(name).asLiveData()
}