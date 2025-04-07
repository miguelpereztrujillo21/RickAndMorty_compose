package com.mperezt.rick.domain.repository

import com.mperezt.rick.domain.models.CharacterResponse

interface ICharactersRepository {
    suspend fun getCharacters(page: Int): CharacterResponse
}