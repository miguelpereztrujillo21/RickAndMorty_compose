package com.mperezt.rick.data.repository

import com.mperezt.rick.data.ApiService
import com.mperezt.rick.data.mappers.toDomain
import com.mperezt.rick.data.models.CharacterResponseDto
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.repository.ICharactersRepository
import jakarta.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) :
    ICharactersRepository{
    override suspend fun getCharacters(page: Int): CharacterResponse {
        return apiService.getCharacters(page).toDomain()
    }

}