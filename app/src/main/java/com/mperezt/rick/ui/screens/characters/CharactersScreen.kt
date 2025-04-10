package com.mperezt.rick.ui.screens.characters

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mperezt.rick.ui.screens.characters.components.CharacterListUI
import com.mperezt.rick.ui.screens.shared.ErrorScreen
import com.mperezt.rick.ui.screens.shared.LoadingScreen

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CharactersEvent.SelectCharacter -> {
                    // Navegación o acción con el personaje seleccionado
                }
                CharactersEvent.OnError -> {
                    Toast.makeText(context, "Error al cargar los personajes", Toast.LENGTH_SHORT).show()
                }
                else -> {}
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
        state.characters != null -> {
            val characters = state.characters?.results.orEmpty()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() // Asegura restricciones claras
            ) {
                item {
                    Text(
                        text = "Rick and Morty Characters",
                        modifier = Modifier.padding(16.dp)
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

            LaunchedEffect(characters) {
                if (!state.isLoading && !viewModel.isLastPage) {
                    viewModel.loadCharacters()
                }
            }
        }
    }
}
