package com.mperezt.rick.ui.screens.characters

import com.mperezt.rick.ui.models.CharacterResponseUi

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: CharacterResponseUi? = null,
    val error: String? = null
)
