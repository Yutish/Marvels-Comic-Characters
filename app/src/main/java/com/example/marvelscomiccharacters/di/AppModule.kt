package com.example.marvelscomiccharacters.di

import com.example.marvelscomiccharacters.data.data_source.dto.MarvelApi
import com.example.marvelscomiccharacters.data.repository.MarvelRepositoryImplementation
import com.example.marvelscomiccharacters.domain.repository.MarvelRepository
import com.example.marvelscomiccharacters.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMarvelApi(): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarvelRepository(api: MarvelApi): MarvelRepository {
        return MarvelRepositoryImplementation(api)
    }
}
