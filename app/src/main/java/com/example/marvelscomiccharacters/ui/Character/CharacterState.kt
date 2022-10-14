package com.example.marvelscomiccharacters.ui.Character

import com.example.marvelscomiccharacters.domain.model.CharacterModel

data class CharacterState(
    val isLoading: Boolean = false,
    val characterDetail: List<CharacterModel> = emptyList(),
    val error: String = ""
)