package com.mperezt.rick.di.repository

import com.mperezt.rick.data.repository.CharactersRepository
import com.mperezt.rick.domain.repository.ICharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CharacterRepositoryModule {
    @Binds
    @Singleton
    fun bindCharacterRepository(
        charactersRepository: CharactersRepository
    ): ICharactersRepository

}