package com.mperezt.rick.data.mappers

import com.mperezt.rick.data.models.CharacterDto
import com.mperezt.rick.data.models.CharacterResponseDto
import com.mperezt.rick.data.models.InfoDto
import com.mperezt.rick.data.models.LocationDto
import com.mperezt.rick.data.models.OriginDto
import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.models.Info
import com.mperezt.rick.domain.models.Location
import com.mperezt.rick.domain.models.Origin

fun CharacterResponseDto.toDomain() = CharacterResponse(
    info = info.toDomain(),
    results = results.map { it.toDomain() }
)

fun InfoDto.toDomain() = Info(
    count = count,
    pages = pages,
    next = next,
    prev = prev
)

fun CharacterDto.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDomain(),
    location = location.toDomain(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun LocationDto.toDomain() = Location(
    name = name,
    url = url
)

fun OriginDto.toDomain() = Origin(
    name = name,
    url = url
)
