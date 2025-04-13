package com.mperezt.rick.ui.screens.characters

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mperezt.rick.ui.screens.characters.components.CharacterListUI
import com.mperezt.rick.ui.screens.characters.components.CharactersFiltersUi
import com.mperezt.rick.ui.screens.shared.ErrorScreen
import com.mperezt.rick.ui.screens.shared.LoadingScreen

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CharactersEvent.NavigateToDetail -> {
                    onNavigateToDetail(event.characterId)
                }
                is CharactersEvent.OnError -> {
                    Toast.makeText(context, "Error al cargar los personajes", Toast.LENGTH_SHORT).show()
                }
                is CharactersEvent.OnCharactesLoaded -> {
                    // Opcional: manejar evento de carga completada
                }
            }
        }
    }

    when {
        state.isLoading && state.characters == null -> {
            LoadingScreen()
        }
        state.error != null && state.characters == null -> {
            ErrorScreen(
                message = state.error.toString(),
                onRetry = { viewModel.loadCharacters() }
            )
        }
        else -> {
            val characters = state.characters?.results.orEmpty()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Componente de filtros (lo añadiremos después)
                item {
                    CharactersFiltersUi(
                        initialFilter = viewModel.currentFilter,
                        onApplyFilter = { filter ->
                            viewModel.applyFilter(filter)
                        }
                    )
                }

                // Lista de personajes
                item {
                    CharacterListUI(characters) { characterId ->
                        viewModel.onCharacterSelected(characterId)
                    }
                }

                item {
                    if (state.isLoading) {
                        LoadingScreen()
                    }
                }
            }

            // Solución para evitar acceder a isLastPage directamente
            LaunchedEffect(characters.size) {
                if (!state.isLoading && characters.isNotEmpty()) {
                    viewModel.loadCharacters()
                }
            }
        }
    }
}