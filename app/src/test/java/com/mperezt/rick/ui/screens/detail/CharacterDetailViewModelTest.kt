package com.mperezt.rick.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.ui.models.GenderUi
import com.mperezt.rick.domain.models.Location
import com.mperezt.rick.domain.models.Origin
import com.mperezt.rick.ui.models.StatusUi
import com.mperezt.rick.domain.usecases.GetCharacterByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CharacterDetailViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val characterId = 1

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCharacterByIdUseCase = mockk()
        savedStateHandle = SavedStateHandle().apply {
            set("characterId", characterId)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load character successfully`() = runTest {
        // Given
        val mockCharacter = createMockCharacter(characterId)
        coEvery { getCharacterByIdUseCase(characterId) } returns mockCharacter

        // When
        viewModel = CharacterDetailViewModel(getCharacterByIdUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertNotNull(state.character)
        assertEquals("Rick Sanchez", state.character?.name)
        assertNull(state.error)
        assertEquals(false, state.isLoading)
        coVerify(exactly = 1) { getCharacterByIdUseCase(characterId) }
    }

    @Test
    fun `init should handle error when loading character fails`() = runTest {
        // Given
        val errorMessage = "Error al cargar el personaje"
        coEvery { getCharacterByIdUseCase(characterId) } throws RuntimeException(errorMessage)

        // When
        viewModel = CharacterDetailViewModel(getCharacterByIdUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertNull(state.character)
        assertEquals(errorMessage, state.error)
        assertEquals(false, state.isLoading)
        coVerify(exactly = 1) { getCharacterByIdUseCase(characterId) }
    }


    private fun createMockCharacter(id: Int = 1): Character {
        return Character(
            id = id,
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
    }
}