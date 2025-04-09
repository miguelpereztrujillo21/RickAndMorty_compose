package com.mperezt.rick.ui.screens.characters

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mperezt.rick.ui.screens.characters.components.CharacterListUI
import com.mperezt.rick.ui.screens.shared.ErrorScreen
import com.mperezt.rick.ui.screens.shared.LoadingScreen

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = hiltViewModel()) {

    // Observa el estado desde el ViewModel
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Para capturar eventos (por ejemplo, mostrar un error o seleccionar un personaje)
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is CharactersEvent.SelectCharacter -> {
                    // Aquí podrías navegar a la pantalla de detalle del personaje
                    // o realizar alguna acción con el id del personaje seleccionado.
                    // Ejemplo:
                    // NavHostController.navigate("characterDetail/${event.characterId}")
                }
                CharactersEvent.OnError -> {
                    Toast.makeText(context, "Error al cargar los personajes", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    // Mostrar pantalla de carga o el contenido, según el estado
    when {
        state.isLoading -> {
            // Pantalla de carga
            LoadingScreen()
        }
        state.error != null && state.characters == null -> {
            // Pantalla de error
            ErrorScreen(
                message = state.error.toString(),
                onRetry = { viewModel.loadCharacters() }
            )
        }
        state.characters != null -> {
            CharacterListUI(characters = state.characters?.results.orEmpty()) { characterId ->
                viewModel.onCharacterSelected(characterId)
            }
        }
    }
}

