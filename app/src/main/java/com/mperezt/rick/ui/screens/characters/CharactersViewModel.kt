package com.mperezt.rick.ui.screens.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.mperezt.rick.domain.models.CharacterFilter
import com.mperezt.rick.domain.repository.ICharactersRepository
import com.mperezt.rick.ui.base.BaseViewModel
import com.mperezt.rick.ui.mappers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: ICharactersRepository
) : BaseViewModel<CharactersState, CharactersEvent>(CharactersState()) {

    private var isLoading = false
    var isLastPage = false
    private var currentPage = 1

    // Estado del filtro actual
    var currentFilter by mutableStateOf(CharacterFilter())
        private set

    init {
        loadCharacters()
    }

    fun applyFilter(filter: CharacterFilter) {
        currentFilter = filter
        resetAndReloadCharacters()
    }

    private fun resetAndReloadCharacters() {
        _state.value = _state.value.copy(
            characters = null,
            error = null,
            isLoading = true
        )
        isLastPage = false
        currentPage = 1
        loadCharacters()
    }

    fun loadCharacters(page: Int = currentPage) {
        if (isLoading || isLastPage) return
        isLoading = true
        _state.value = _state.value.copy(isLoading = true)

        executeUseCase(
            useCase = {
                charactersRepository.getCharacters(
                    page = page,
                    name = currentFilter.name,
                    status = currentFilter.status?.value,
                    species = currentFilter.species,
                    type = currentFilter.type,
                    gender = currentFilter.gender?.value
                )
            },
            success = { response ->
                val characterResponseUi = response.toUi()
                val updatedCharacters = (_state.value.characters?.results ?: emptyList()) + characterResponseUi.results

                _state.value = _state.value.copy(
                    isLoading = false,
                    characters = characterResponseUi.copy(results = updatedCharacters),
                    error = null
                )

                isLastPage = characterResponseUi.results.isEmpty()
                currentPage++

                _state.value.characters?.let {
                    viewModelScope.launch {
                        emitEvent(CharactersEvent.OnCharactesLoaded(it))
                    }
                }
            },
            error = { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
                viewModelScope.launch {
                    emitEvent(CharactersEvent.OnError)
                }
            }
        )
    }

    fun onCharacterSelected(characterId: Int) {
        viewModelScope.launch {
            emitEvent(CharactersEvent.NavigateToDetail(characterId))
        }
    }
}