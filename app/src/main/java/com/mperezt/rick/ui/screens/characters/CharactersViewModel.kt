package com.mperezt.rick.ui.screens.characters

import android.util.Log
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.usecases.GetCharactersUseCase
import com.mperezt.rick.ui.base.BaseViewModel
import com.mperezt.rick.ui.mappers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharactersEvent>() {

    init {
        loadCharacters()
    }

    private fun loadCharacters(page: Int = 1) {
        executeUseCase(
            useCase = { getCharactersUseCase(page) },
            success = { response: CharacterResponse ->
                _event.value = CharactersEvent.OnCharactesLoaded(response.toUi())
            },
            error = { error -> Log.e(tag, "Error loading characters: ${error.message}") }
        )
    }

}