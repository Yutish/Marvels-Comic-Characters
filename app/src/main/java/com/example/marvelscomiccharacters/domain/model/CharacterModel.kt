package com.example.marvelscomiccharacters.domain.model

/**
 * CharacterModel represent a character.
 */
data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExt: String,
    val comics: List<String>,
)
