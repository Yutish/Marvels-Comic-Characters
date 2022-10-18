package com.example.marvelscomiccharacters.ui.CharactersList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelscomiccharacters.domain.model.CharacterModel
import com.example.marvelscomiccharacters.domain.use_cases.BookmarkedCharacterUseCase
import com.example.marvelscomiccharacters.domain.use_cases.CharactersUseCase
import com.example.marvelscomiccharacters.domain.use_cases.SearchCharacterCase
import com.example.marvelscomiccharacters.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [CharactersViewModel] designed to store and manage UI-related data in a lifecycle conscious way.
 * In addition, background work such as fetching network results can continue through configuration
 * changes and deliver results after the new Fragment or Activity is available.
 *
 *
 * @param [charactersUseCase], [searchCharacterCase] is the bridge between viewModel and Repository
 */
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase,
    private val searchCharacterCase: SearchCharacterCase,
    private val bookmarkedCharacterUseCase: BookmarkedCharacterUseCase
) : ViewModel() {
    private val _marvelValue = MutableStateFlow(MarvelListState())
    var marvelValue: StateFlow<MarvelListState> = _marvelValue

    fun getSearchedCharacters(search: String) = viewModelScope.launch(Dispatchers.IO) {
        searchCharacterCase.invoke(search = search).collect {
            when (it) {
                is State.Success -> {
                    _marvelValue.value = MarvelListState(charactersList = it.data ?: emptyList())
                    Log.d("toCharacter", marvelValue.value.toString())
                }
                is State.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                    Log.d("loading", it.data.toString())
                }
                is State.Error -> {
                    _marvelValue.value =
                        MarvelListState(error = it.message ?: "An Unexpected Error")
                    Log.d("Error", it.data.toString())
                }
            }
        }
    }

    fun getAllCharactersData(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        charactersUseCase(offset).collect {
            when (it) {
                is State.Success -> {
                    _marvelValue.value = MarvelListState(charactersList = it.data ?: emptyList())
                    Log.d("toCharacter", marvelValue.value.toString())
                }
                is State.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                    Log.d("loading", it.data.toString())
                }
                is State.Error -> {
                    _marvelValue.value =
                        MarvelListState(error = it.message ?: "An Unexpected Error")
                    Log.d("Error", it.data.toString())
                }
            }
        }
    }

    fun getBookmarkedCharacterData() = viewModelScope.launch(Dispatchers.IO) {
        bookmarkedCharacterUseCase.invoke().collect {
            when (it) {
                is State.Success -> {
                    _marvelValue.value = MarvelListState(charactersList = it.data ?: emptyList())
                    Log.d("toCharacter", marvelValue.value.toString())
                }
                is State.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                    Log.d("loading", it.data.toString())
                }
                is State.Error -> {
                    _marvelValue.value =
                        MarvelListState(error = it.message ?: "An Unexpected Error")
                    Log.d("Error", it.data.toString())
                }
            }
        }
    }

    fun setBookmarkValues(characterModel: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkedCharacterUseCase.setBookmarkData(characterModel)
        }
    }
}