package com.mperezt.rick.ui.screens.characters

import com.mperezt.rick.ui.models.CharacterResponseUi

sealed class CharactersEvent {
    data class SelectCharacter(val characterId: Int) : CharactersEvent()
    class OnCharactesLoaded(val characters: CharacterResponseUi) : CharactersEvent()
    data object OnError : CharactersEvent()
}