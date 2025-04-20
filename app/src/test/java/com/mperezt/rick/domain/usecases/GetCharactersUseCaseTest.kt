package com.mperezt.rick.domain.usecases

import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.ui.models.GenderUi
import com.mperezt.rick.domain.models.Info
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

class GetCharactersUseCaseTest {

    private lateinit var repository: ICharactersRepository
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val defaultPage = 1

    @Before
    fun setup() {
        repository = mockk()
        getCharactersUseCase = GetCharactersUseCase(repository)
    }

    private fun createMockCharacter(
        id: Int,
        name: String,
        status: String = StatusUi.Alive.value,
        gender: String = GenderUi.Male.value
    ): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = "Human",
            type = "",
            gender = gender,
            origin = Origin("Earth", ""),
            location = Location("Earth", ""),
            image = "https://example.com/$name.jpg",
            created = "2023-01-01",
            episode = listOf("S01E01", "S01E02"),
            url = "https://example.com/${name.lowercase()}"
        )
    }

    private fun mockRepositoryResponse(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null,
        characters: List<Character>,
        info: Info = Info(
            count = characters.size,
            pages = 1,
            next = null,
            prev = null
        )
    ) {
        val response = CharacterResponse(info = info, results = characters)

        coEvery {
            repository.getCharacters(page, name, status, species, type, gender)
        } returns response
    }

    @Test
    fun `invoke should return characters when repository returns success`() = runTest {
        // Given
        val mockCharacters = listOf(
            createMockCharacter(1, "Rick"),
            createMockCharacter(2, "Morty")
        )

        mockRepositoryResponse(defaultPage, characters = mockCharacters)

        // When
        val result = getCharactersUseCase(defaultPage)

        // Then
        assertEquals(2, result.results.size)
        assertEquals("Rick", result.results[0].name)
        coVerify(exactly = 1) { repository.getCharacters(defaultPage, null, null, null, null, null) }
    }

    @Test
    fun `invoke should apply filters correctly`() = runTest {
        // Given
        val name = "Rick"
        val status = StatusUi.Alive.value
        val species = "Human"
        val type = ""
        val gender = GenderUi.Male.value

        val mockCharacters = listOf(createMockCharacter(1, "Rick"))

        mockRepositoryResponse(
            page = defaultPage,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            characters = mockCharacters
        )

        // When
        val result = getCharactersUseCase(defaultPage, name, status, species, type, gender)

        // Then
        assertEquals(1, result.results.size)
        assertEquals("Rick", result.results[0].name)
        coVerify(exactly = 1) {
            repository.getCharacters(defaultPage, name, status, species, type, gender)
        }
    }

    @Test
    fun `invoke should propagate exception when repository fails`() = runTest {
        // Given
        val expectedException = RuntimeException("Error al obtener personajes")

        coEvery { repository.getCharacters(defaultPage, null, null, null, null, null) } throws expectedException

        // When/Then
        try {
            getCharactersUseCase(defaultPage)
            assert(false) { "Se esperaba una excepción pero no se lanzó ninguna" }
        } catch (e: Exception) {
            assertEquals(expectedException, e)
        }

        coVerify(exactly = 1) { repository.getCharacters(defaultPage, null, null, null, null, null) }
    }
}