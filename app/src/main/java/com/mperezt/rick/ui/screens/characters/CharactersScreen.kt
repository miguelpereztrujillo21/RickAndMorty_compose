package com.mperezt.rick.ui.screens.characters

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.mperezt.rick.R
import com.mperezt.rick.ui.screens.characters.components.CharacterListItem
import com.mperezt.rick.ui.screens.characters.components.CharactersFiltersUi
import com.mperezt.rick.ui.screens.shared.ErrorScreen
import com.mperezt.rick.ui.screens.shared.LoadingScreen
import com.mperezt.rick.ui.theme.IconSize
import com.mperezt.rick.ui.theme.Padding

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onCharactersClick: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CharactersEvent.NavigateToDetail -> {
                    onCharactersClick(event.characterId)
                }
                is CharactersEvent.OnError -> {
                    Toast.makeText(context, R.string.character_carge_error, Toast.LENGTH_SHORT)
                        .show()
                }
                is CharactersEvent.OnCharactesLoaded -> {}
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                item{
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(Padding.Small)
                            .height(IconSize.Logo),
                        alignment = Alignment.Center
                    )
                }
                item {
                    CharactersFiltersUi(
                        initialFilter = viewModel.currentFilter,
                        onApplyFilter = { filter ->
                            viewModel.applyFilter(filter)
                        }
                    )
                }

                items(characters) { character ->
                    CharacterListItem(
                        character = character,
                        onClick = { viewModel.onCharacterSelected(character.id) }
                    )
                    if (character == characters.lastOrNull() && !state.isLoading && !viewModel.isLastPage) {
                        LaunchedEffect(key1 = character.id) {
                            viewModel.loadCharacters()
                        }
                    }
                }

                item {
                    if (state.isLoading && characters.isNotEmpty()) {
                        LoadingScreen()
                    }
                }
            }
        }
    }
}