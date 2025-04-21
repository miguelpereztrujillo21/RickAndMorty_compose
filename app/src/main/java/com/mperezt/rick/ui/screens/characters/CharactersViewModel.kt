package com.mperezt.rick.ui.screens.characters

import com.mperezt.rick.domain.usecases.GetCharactersUseCase
import com.mperezt.rick.ui.base.BaseViewModel
import com.mperezt.rick.ui.mappers.toUi
import com.mperezt.rick.ui.models.CharacterFilterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharactersState>(CharactersState()) {

    private var currentPage = 1

    init {
        loadCharacters()
    }

    fun applyFilter(filter: CharacterFilterUi) {
        _state.value = _state.value.copy(filter = filter)
        resetAndReloadCharacters()
    }

    private fun resetAndReloadCharacters() {
        _state.value = _state.value.copy(
            characters = null,
            error = null,
            isLoading = true,
            isLastPage = false
        )
        currentPage = 1
        loadCharacters()
    }

    fun loadCharacters(page: Int = currentPage) {
        if (_state.value.isLastPage ) return
        _state.value = _state.value.copy(isLoading = true)

        executeUseCase(
            useCase = {
                getCharactersUseCase(
                    page = page,
                    name = _state.value.filter.name,
                    status = _state.value.filter.status?.value,
                    species = _state.value.filter.species,
                    type = _state.value.filter.type,
                    gender = _state.value.filter.gender?.value
                )
            },
            success = { response ->
                val characterResponseUi = response.toUi()
                val updatedCharacters = (_state.value.characters?.results ?: emptyList()) + characterResponseUi.results
                _state.value = _state.value.copy(
                    isLoading = false,
                    characters = characterResponseUi.copy(results = updatedCharacters),
                    error = null,
                    isLastPage = page >= characterResponseUi.info.pages || characterResponseUi.results.isEmpty()
                )

                if (!_state.value.isLastPage) {
                    currentPage++
                }

            },
            error = { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        )
    }
}