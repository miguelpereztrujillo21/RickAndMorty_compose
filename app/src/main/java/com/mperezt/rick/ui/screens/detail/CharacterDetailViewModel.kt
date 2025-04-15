package com.mperezt.rick.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mperezt.rick.domain.usecases.GetCharacterByIdUseCase
import com.mperezt.rick.ui.base.BaseViewModel
import com.mperezt.rick.ui.mappers.toUi
import com.mperezt.rick.ui.models.CharacterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val character: CharacterUi? = null,
    val error: String? = null
)

sealed class CharacterDetailEvent {
    data object OnBack : CharacterDetailEvent()
}

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CharacterDetailState, CharacterDetailEvent>(CharacterDetailState()) {

    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val character = getCharacterByIdUseCase(characterId).toUi()
                _state.update {
                    it.copy(
                        isLoading = false,
                        character = character,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            emitEvent(CharacterDetailEvent.OnBack)
        }
    }
}