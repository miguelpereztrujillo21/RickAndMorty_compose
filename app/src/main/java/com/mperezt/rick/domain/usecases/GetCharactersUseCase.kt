package com.mperezt.rick.domain.usecases

import com.mperezt.rick.data.repository.CharactersRepository
import com.mperezt.rick.domain.models.CharacterResponse
import jakarta.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository){
    suspend operator fun invoke(page: Int): CharacterResponse = repository.getCharacters(page)
}