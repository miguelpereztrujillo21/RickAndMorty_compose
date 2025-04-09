package com.mperezt.rick.ui.screens.characters

import androidx.lifecycle.viewModelScope
import com.mperezt.rick.domain.usecases.GetCharactersUseCase
import com.mperezt.rick.ui.base.BaseViewModel
import com.mperezt.rick.ui.mappers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharactersState, CharactersEvent>(initialState = CharactersState()) {

    init {
        loadCharacters()
    }

    fun loadCharacters(page: Int = 1) {
        _state.value = _state.value.copy(isLoading = true)

        executeUseCase(
            useCase = { getCharactersUseCase(page) },
            success = { response ->
                _state.value = CharactersState(
                    isLoading = false,
                    characters = response.toUi(),
                    error = null
                )
                viewModelScope.launch {
                    emitEvent(CharactersEvent.OnCharactesLoaded(response.toUi()))
                }
            },
            error = { throwable ->
                _state.value = CharactersState(isLoading = false, error = throwable.message)
                viewModelScope.launch {
                    emitEvent(CharactersEvent.OnError)
                }
            }
        )
    }

    fun onCharacterSelected(characterId: Int) {
        viewModelScope.launch {
            emitEvent(CharactersEvent.SelectCharacter(characterId))
        }
    }
}
