package com.example.marvelscomiccharacters.domain.use_cases

import com.example.marvelscomiccharacters.domain.model.CharacterModel
import com.example.marvelscomiccharacters.domain.repository.MarvelRepository
import com.example.marvelscomiccharacters.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    operator fun invoke(offset: Int): Flow<State<List<CharacterModel>>> = flow {
        try {
            emit(State.Loading<List<CharacterModel>>())
            val list = repository.getAllCharacters(offset = offset)
            emit(State.Success<List<CharacterModel>>(list))
        } catch (e: HttpException) {
            emit(State.Error<List<CharacterModel>>(e.printStackTrace().toString()))
        } catch (e: IOException) {
            emit(State.Error<List<CharacterModel>>(e.printStackTrace().toString()))
        }
    }
}