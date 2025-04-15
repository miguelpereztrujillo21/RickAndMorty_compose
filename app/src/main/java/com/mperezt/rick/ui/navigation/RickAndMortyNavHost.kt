package com.mperezt.rick.ui.navigation

import CharacterDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mperezt.rick.ui.screens.characters.CharactersScreen

@Composable
fun RickAndMortyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.CHARACTERS_ROUTE,
        modifier = modifier
    ) {
        composable(NavRoutes.CHARACTERS_ROUTE) {
            CharactersScreen(
                onCharactersClick = { characterId ->
                    navController.navigate(NavRoutes.characterDetail(characterId))
                }
            )
        }

        composable(
            route = NavRoutes.CHARACTER_DETAIL_ROUTE,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) {
            CharacterDetailScreen(
                characterId = it.arguments?.getInt("characterId") ?: 0,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}