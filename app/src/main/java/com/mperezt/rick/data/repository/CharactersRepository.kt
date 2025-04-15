package com.mperezt.rick.data.repository

import com.mperezt.rick.data.ApiService
import com.mperezt.rick.data.mappers.toDomain
import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.repository.ICharactersRepository
import jakarta.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) :
    ICharactersRepository {
    override suspend fun getCharacters(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterResponse {
        return apiService.getCharacters(page, name, status, species, type, gender).toDomain()
    }

    override suspend fun getCharacterById(id: Int): Character {
        return apiService.getCharacterById(id).toDomain()
    }
}