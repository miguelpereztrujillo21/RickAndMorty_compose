package com.mperezt.rick.ui.mappers

import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.models.Info
import com.mperezt.rick.domain.models.Location
import com.mperezt.rick.ui.models.CharacterResponseUi
import com.mperezt.rick.ui.models.CharacterUi
import com.mperezt.rick.ui.models.InfoUi
import com.mperezt.rick.ui.models.LocationUi

fun CharacterResponse.toUi() =
    CharacterResponseUi(info = info.toUi(), results = results.map { it.toUi() })

fun Info.toUi() = InfoUi(
    count = count, pages = pages, next = next, prev = prev
)

fun Character.toUi() = CharacterUi(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toUi(),
    location = location.toUi(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun Location.toUi() = LocationUi(
    name = name, url = url
)