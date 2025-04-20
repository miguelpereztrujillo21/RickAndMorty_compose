package com.mperezt.rick.ui.screens.characters

import com.mperezt.rick.ui.models.CharacterFilterUi
import com.mperezt.rick.ui.models.CharacterResponseUi

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: CharacterResponseUi? = null,
    val filter: CharacterFilterUi = CharacterFilterUi(),
    val isLastPage: Boolean = false,
    val error: String? = null
)
