package com.mperezt.rick.data

import com.mperezt.rick.data.models.CharacterResponseDto
import com.mperezt.rick.domain.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
@GET("character")
suspend fun getCharacters(@Query("page") page: Int): CharacterResponseDto
}