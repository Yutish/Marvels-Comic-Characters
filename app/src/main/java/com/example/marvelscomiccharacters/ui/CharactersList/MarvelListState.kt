package com.example.marvelscomiccharacters.ui.CharactersList

import com.example.marvelscomiccharacters.domain.model.CharacterModel

data class MarvelListState(
    val isLoading: Boolean = false,
    val charactersList: List<CharacterModel> = emptyList(),
    val error: String = ""
)