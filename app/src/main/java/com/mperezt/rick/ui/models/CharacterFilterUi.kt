package com.mperezt.rick.ui.models

data class CharacterFilterUi(
    val name: String? = null,
    val status: StatusUi? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: GenderUi? = null
)

enum class StatusUi(val value: String) {
    Alive("alive"),
    Dead("dead"),
    Unknown("unknown")
}

enum class GenderUi(val value: String) {
    Female("female"),
    Male("male"),
    Genderless("genderless"),
    Unknown("unknown")
}