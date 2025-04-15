package com.mperezt.rick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mperezt.rick.ui.models.CharacterUi
import com.mperezt.rick.ui.models.LocationUi
import com.mperezt.rick.ui.models.OriginUi
import com.mperezt.rick.ui.navigation.AppNavHost
import com.mperezt.rick.ui.screens.characters.components.CharacterListItem
import com.mperezt.rick.ui.theme.RickAndMorty_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMorty_composeTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMorty_composeTheme {
        CharacterListItem(
            character = CharacterUi(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                origin = OriginUi(name = "Earth", url = ""),
                location = LocationUi("Earth", "https://rickandmortyapi.com/api/location/1"),
                episode = emptyList(),
                url = "",
                created = ""
            ),
            onClick = {}
        )
    }
}