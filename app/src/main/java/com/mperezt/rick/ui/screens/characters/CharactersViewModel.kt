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

    private var currentPage = 1
    var isLastPage = false
    private var isLoading = false

    init {
        loadCharacters()
    }

    fun loadCharacters(page: Int = currentPage) {
        if (isLoading || isLastPage) return

        isLoading = true
        _state.value = _state.value.copy(isLoading = true)

        executeUseCase(
            useCase = { getCharactersUseCase(page) },
            success = { response ->
                val updatedCharacters = (_state.value.characters?.results ?: emptyList()) + response.toUi().results
                _state.value = _state.value.copy(
                    isLoading = false,
                    characters = response.toUi().copy(results = updatedCharacters),
                    error = null
                )
                isLastPage = response.toUi().results.isEmpty()
                currentPage++
                viewModelScope.launch {
                    _state.value.characters?.let { CharactersEvent.OnCharactesLoaded(it) }
                        ?.let { emitEvent(it) }
                }
            },
            error = { throwable ->
                _state.value = _state.value.copy(isLoading = false, error = throwable.message)
                viewModelScope.launch {
                    emitEvent(CharactersEvent.OnError)
                }
            }
        )
        isLoading = false
    }

    fun onCharacterSelected(characterId: Int) {
        viewModelScope.launch {
            emitEvent(CharactersEvent.SelectCharacter(characterId))
        }
    }
}