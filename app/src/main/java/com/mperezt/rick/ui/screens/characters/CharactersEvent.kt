package com.mperezt.rick.ui.screens.characters

import com.mperezt.rick.ui.models.CharacterResponseUi

sealed class CharactersEvent {
    data class NavigateToDetail(val characterId: Int) : CharactersEvent()
    data class OnCharactesLoaded(val characters: CharacterResponseUi) : CharactersEvent()
    object OnError : CharactersEvent()
}