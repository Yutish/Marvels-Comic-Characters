package com.example.marvelscomiccharacters.domain.repository

import com.example.marvelscomiccharacters.data.data_source.dto.CharactersDTO.CharactersDTO
import com.example.marvelscomiccharacters.domain.model.CharacterModel

interface MarvelRepository {

    suspend fun getAllCharacters(offset: Int): List<CharacterModel>
    suspend fun getAllSearchedCharacters(search: String): CharactersDTO
    suspend fun getCharacterById(id: String): List<CharacterModel>
}