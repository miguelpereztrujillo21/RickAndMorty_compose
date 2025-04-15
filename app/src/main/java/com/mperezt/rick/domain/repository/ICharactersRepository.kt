package com.mperezt.rick.domain.repository

import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterResponse

interface ICharactersRepository {
    suspend fun getCharacters(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): CharacterResponse

    suspend fun getCharacterById(id: Int): Character
}