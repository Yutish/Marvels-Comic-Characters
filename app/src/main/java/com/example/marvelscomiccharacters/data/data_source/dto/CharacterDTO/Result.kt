package com.example.marvelscomiccharacters.data.data_source.dto.CharacterDTO

import com.example.marvelscomiccharacters.domain.model.CharacterModel

/**
 * converting the resultant DTO [CharacterDTO] to Character Model [CharacterModel]
 */
data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
) {
    /**
     *  @returns [CharacterModel]
     */
    fun toCharacter(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            description = description,
            thumbnail = thumbnail.path,
            thumbnailExt = thumbnail.extension,
            comics = comics.items.map {
                it.name
            }
        )
    }
}