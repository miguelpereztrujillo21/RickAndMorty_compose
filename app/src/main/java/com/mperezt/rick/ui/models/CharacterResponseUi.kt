package com.mperezt.rick.ui.models

data class CharacterResponseUi(
    val info: InfoUi,
    val results: List<CharacterUi>
)

data class InfoUi(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterUi(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationUi,
    val location: LocationUi,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationUi(
    val name: String,
    val url: String
)