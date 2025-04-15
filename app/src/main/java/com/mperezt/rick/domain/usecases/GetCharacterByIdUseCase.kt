package com.mperezt.rick.domain.usecases

import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.repository.ICharactersRepository
import javax.inject.Inject


class GetCharacterByIdUseCase @Inject constructor(
    private val repository: ICharactersRepository
) {
    suspend operator fun invoke(id: Int): Character {
        return repository.getCharacterById(id)
    }
}