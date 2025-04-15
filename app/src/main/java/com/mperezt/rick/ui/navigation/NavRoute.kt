package com.mperezt.rick.ui.navigation

object NavRoutes {
    const val CHARACTERS_ROUTE = "characters"
    const val CHARACTER_DETAIL_ROUTE = "character/{characterId}"

    fun characterDetail(characterId: Int): String = "character/$characterId"
}