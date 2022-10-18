package com.example.marvelscomiccharacters.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelscomiccharacters.domain.model.CharacterModel

@Entity
data class DatabaseCharacter(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExt: String
)

@Entity
data class BookmarkedCharacter(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExt: String,
)

fun List<DatabaseCharacter>.asDomainModel(): List<CharacterModel> {
    return map {
        CharacterModel(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail,
            thumbnailExt = it.thumbnailExt,
            comics = emptyList()
        )
    }
}

fun List<BookmarkedCharacter>.asBookmarkedDomainModel(): List<CharacterModel> {
    return map {
        CharacterModel(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail,
            thumbnailExt = it.thumbnailExt,
            comics = emptyList(),
        )
    }
}