package com.mperezt.rick.ui.screens.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mperezt.rick.domain.models.Character
import com.mperezt.rick.domain.models.CharacterFilter
import com.mperezt.rick.domain.models.CharacterResponse
import com.mperezt.rick.domain.models.Gender
import com.mperezt.rick.domain.models.Info
import com.mperezt.rick.domain.models.Location
import com.mperezt.rick.domain.models.Origin
import com.mperezt.rick.domain.models.Status
import com.mperezt.rick.domain.repository.ICharactersRepository
import com.mperezt.rick.domain.usecases.GetCharactersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ICharactersRepository
    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        coEvery {
            repository.getCharacters(
                any(), any(), any(), any(), any(), any()
            )
        } returns getMockCharacterResponse()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial debe ser carga`() = runTest {
        // When - Crear viewModel (dispara init { loadCharacters() })
        viewModel = CharactersViewModel(GetCharactersUseCase(repository))

        // Then - Verificar estado inicial
        assertTrue(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.characters)
        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `loadCharacters debe actualizar el estado con personajes cuando es exitoso`() = runTest {
        // Given
        viewModel = CharactersViewModel(GetCharactersUseCase(repository))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.state.value) {
            assertFalse(isLoading)
            assertNotNull(characters)
            assertEquals(2, characters?.results?.size)
            assertNull(error)
        }
    }

    @Test
    fun `applyFilter debe resetear la paginación y cargar con nuevos filtros`() = runTest {

        viewModel = CharactersViewModel(GetCharactersUseCase(repository))
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)

        val nuevoFiltro = CharacterFilter(name = "Rick", status = Status.Alive)

        coVerify(exactly = 1) {
            repository.getCharacters(1, null, null, null, null, null)
        }

        viewModel.applyFilter(nuevoFiltro)

        assertTrue(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.characters)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) {
            repository.getCharacters(1, "Rick", Status.Alive.value, null, null, null)
        }

        assertEquals(nuevoFiltro, viewModel.currentFilter)
    }

    @Test
    fun `onCharacterSelected debe emitir evento de navegación`() = runTest {
        // Given
        viewModel = CharactersViewModel(GetCharactersUseCase(repository))
        val eventos = mutableListOf<CharactersEvent>()

        // When
        val job = launch { viewModel.events.toList(eventos) }
        viewModel.onCharacterSelected(42)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(eventos.any { it is CharactersEvent.NavigateToDetail && it.characterId == 42 })
        job.cancel()
    }

    private fun getMockCharacterResponse() = CharacterResponse(
        info = Info(
            count = 2,
            pages = 1,
            next = null,
            prev = null
        ),
        results = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = Status.Alive.value,
                species = "Human",
                type = "",
                gender = Gender.Male.value,
                origin = Origin("Earth", ""),
                location = Location("Earth", ""),
                image = "https://example.com/rick.jpg",
                created = "2023-01-01",
                episode = listOf("S01E01", "S01E02"),
                url = "https://example.com/rick"
            ),
            Character(
                id = 2,
                name = "Morty Smith",
                status = Status.Alive.value,
                species = "Human",
                type = "",
                gender = Gender.Male.value,
                origin = Origin("Earth", ""),
                location = Location("Earth", ""),
                image = "https://example.com/morty.jpg",
                episode = listOf("S01E01", "S01E02"),
                url = "https://example.com/rick",
                created = "2023-01-01"
            )
        )
    )
}