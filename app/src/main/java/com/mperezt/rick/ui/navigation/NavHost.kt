package com.mperezt.rick.ui.navigation

import CharacterDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mperezt.rick.ui.screens.characters.CharactersScreen

object AppDestinations {
    const val CHARACTERS_ROUTE = "characters"
    const val CHARACTER_DETAIL_ROUTE = "character/{characterId}"

    fun characterDetailRoute(characterId: Int): String = "character/$characterId"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = AppDestinations.CHARACTERS_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestinations.CHARACTERS_ROUTE) {
            CharactersScreen(
                onCharactersClick = { characterId ->
                    navController.navigate(AppDestinations.characterDetailRoute(characterId))
                }
            )
        }

        composable(
            route = AppDestinations.CHARACTER_DETAIL_ROUTE,
            arguments = listOf(
                navArgument("characterId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterDetailScreen(
                characterId = characterId,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}