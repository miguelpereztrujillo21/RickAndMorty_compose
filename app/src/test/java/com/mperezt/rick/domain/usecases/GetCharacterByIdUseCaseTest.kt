package com.mperezt.rick.domain.usecases

import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.ui.models.GenderUi
import com.mperezt.rick.domain.models.Location
import com.mperezt.rick.domain.models.Origin
import com.mperezt.rick.ui.models.StatusUi
import com.mperezt.rick.domain.repository.ICharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCharacterByIdUseCaseTest {

    private lateinit var repository: ICharactersRepository
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        getCharacterByIdUseCase = GetCharacterByIdUseCase(repository)
    }

    @Test
    fun `invoke should return character when repository returns success`() = runTest {
        // Given
        val characterId = 1
        val expectedCharacter = Character(
            id = characterId,
            name = "Rick Sanchez",
            status = StatusUi.Alive.value,
            species = "Human",
            type = "",
            gender = GenderUi.Male.value,
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "https://example.com/rick.jpg",
            created = "2023-01-01",
            episode = listOf("S01E01", "S01E02"),
            url = "https://example.com/rick"
        )

        coEvery { repository.getCharacterById(characterId) } returns expectedCharacter

        // When
        val result = getCharacterByIdUseCase(characterId)

        // Then
        assertEquals(expectedCharacter, result)
        coVerify(exactly = 1) { repository.getCharacterById(characterId) }
    }

    @Test
    fun `invoke should propagate exception when repository fails`() = runTest {
        // Given
        val characterId = 1
        val expectedException = RuntimeException("Error al obtener personaje")

        coEvery { repository.getCharacterById(characterId) } throws expectedException

        // When/Then
        try {
            getCharacterByIdUseCase(characterId)
            assert(false) { "Se esperaba una excepción pero no se lanzó ninguna" }
        } catch (e: Exception) {
            assertEquals(expectedException, e)
        }

        coVerify(exactly = 1) { repository.getCharacterById(characterId) }
    }
}