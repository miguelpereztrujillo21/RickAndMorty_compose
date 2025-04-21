package com.mperezt.rick.domain.usecases

import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.repository.ICharactersRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: ICharactersRepository
) {
    suspend operator fun invoke(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): CharacterResponse {
        delay(400)
        return repository.getCharacters(
            page = page,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
    }
}