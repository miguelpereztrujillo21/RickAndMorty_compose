
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mperezt.rick.ui.components.ErrorMessage
import com.mperezt.rick.ui.screens.characters.components.CharacterDetailContent
import com.mperezt.rick.ui.screens.detail.CharacterDetailEvent
import com.mperezt.rick.ui.screens.detail.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBackPressed: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CharacterDetailEvent.OnBack -> onBackPressed()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                ErrorMessage(
                    error = state.error ?: "Error desconocido",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.character != null -> {
                CharacterDetailContent(
                    character = state.character!!,
                    onBackPressed = onBackPressed
                )
            }
        }
    }
}