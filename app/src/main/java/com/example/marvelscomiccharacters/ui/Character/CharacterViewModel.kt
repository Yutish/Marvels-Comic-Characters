package com.example.marvelscomiccharacters.ui.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelscomiccharacters.domain.use_cases.CharacterUseCase
import com.example.marvelscomiccharacters.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [CharacterViewModel] designed to store and manage UI-related data in a lifecycle conscious way.
 * In addition, background work such as fetching network results can continue through configuration
 * changes and deliver results after the new Fragment or Activity is available.
 *
 * @param [characterUseCase] is the brigde between viewModel and Repository
 */
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val _characterValue = MutableStateFlow(CharacterState())
    val characterValue: StateFlow<CharacterState> = _characterValue

    fun getCharacterByIdValue(id: String) = viewModelScope.launch {
        characterUseCase(id).collect {
            when (it) {
                is State.Success -> {
                    _characterValue.value = CharacterState(
                        characterDetail = it.data ?: emptyList()
                    )
                }
                is State.Loading -> {
                    _characterValue.value = CharacterState(isLoading = true)
                }
                is State.Error -> {
                    _characterValue.value =
                        CharacterState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }
}