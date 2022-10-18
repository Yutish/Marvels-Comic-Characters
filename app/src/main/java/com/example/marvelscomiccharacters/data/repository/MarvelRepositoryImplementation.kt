package com.example.marvelscomiccharacters.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.marvelscomiccharacters.MarvelApplication
import com.example.marvelscomiccharacters.data.data_source.dto.CharactersDTO.CharactersDTO
import com.example.marvelscomiccharacters.data.data_source.dto.MarvelApi
import com.example.marvelscomiccharacters.database.*
import com.example.marvelscomiccharacters.domain.model.CharacterModel
import com.example.marvelscomiccharacters.domain.repository.MarvelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 * Implementation for [MarvelRepository]
 *
 * @param MarvelApi
 */
class MarvelRepositoryImplementation @Inject constructor(
    private val api: MarvelApi
) : MarvelRepository {

    /**
     * Loading the data form the internet, if network connection is available and caching the data,
     * else loading the cached data from the database
     *
     * @returns all the characters in form of List of [CharacterModel]
     */
    override suspend fun getAllCharacters(offset: Int): List<CharacterModel> {

        if (checkNetworkConnection()) {
            val resultCharacterDTO = api.getAllCharacters(offset = offset.toString())
            val characterModel = resultCharacterDTO.data.results.map { it.toCharacter() }
            populateDatabase(characterModel)

        }
        return withContext(Dispatchers.IO) {
            (getDatabase(MarvelApplication.instance).characterDao.getCharacters()
                .asDomainModel())
        }
    }

    /**
     * Performs search function over network call.
     *
     * @returns [CharacterDTO]
     */
    override suspend fun getAllSearchedCharacters(search: String): CharactersDTO {
        return api.getAllSearchedCharacters(search = search)
    }

    /**
     * Loading the data form the internet, if network connection is available and caching the data,
     * else loading the cached data from the database
     *
     * this is for the detailed view of a character
     *
     * @returns the characters details in form of List of [CharacterModel]
     */
    override suspend fun getCharacterById(id: String): List<CharacterModel> {
        return if (checkNetworkConnection()) {
            api.getCharacterById(id).data.results.map { it.toCharacter() }
        } else
            withContext(Dispatchers.IO) {
                (getDatabase(MarvelApplication.instance).characterDao.getCharactersById(id.toInt())
                    .asDomainModel())
            }

    }

    override suspend fun getAllBookmarkedCharacters(): List<CharacterModel> {
        return withContext(Dispatchers.IO) {
            (getDatabase(MarvelApplication.instance).characterDao.getAllBookmarkedCharacters()
                .asBookmarkedDomainModel())
        }
    }


    override suspend fun setBookmarkData(characterModel: CharacterModel) {
        return withContext(Dispatchers.IO) {
            getDatabase(MarvelApplication.instance).characterDao.insertBookmark(
                toBookmarkedCharacter(characterModel)
            )
        }

    }

    private suspend fun populateDatabase(list: List<CharacterModel>) {
        withContext(Dispatchers.IO) {
            for (item in list) {
                getDatabase(MarvelApplication.instance).characterDao.insertAll(toDatabaseModel(item))
            }
        }
    }

    private fun toDatabaseModel(item: CharacterModel): DatabaseCharacter {
        return DatabaseCharacter(
            id = item.id,
            name = item.name,
            description = item.description,
            thumbnail = item.thumbnail,
            thumbnailExt = item.thumbnailExt,
        )
    }

    private fun toBookmarkedCharacter(item: CharacterModel): BookmarkedCharacter {
        return BookmarkedCharacter(
            id = item.id,
            name = item.name,
            description = item.description,
            thumbnail = item.thumbnail,
            thumbnailExt = item.thumbnailExt,
        )
    }

    /**
     *  check for the mobile device is connected to internet/network or not
     */
    private fun checkNetworkConnection(): Boolean {
        val connectivityManager =
            MarvelApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                return false
            }
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }
}