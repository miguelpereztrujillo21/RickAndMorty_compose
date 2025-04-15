package com.mperezt.rick.domain.models

data class CharacterFilter(
    val name: String? = null,
    val status: Status? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: Gender? = null
)

enum class Status(val value: String) {
    Alive("alive"),
    Dead("dead"),
    Unknown("unknown")
}

enum class Gender(val value: String) {
    Female("female"),
    Male("male"),
    Genderless("genderless"),
    Unknown("unknown")
}