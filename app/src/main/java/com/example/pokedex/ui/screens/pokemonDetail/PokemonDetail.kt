package com.example.pokedex.ui.screens.pokemonDetail

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedex.R
import com.example.pokedex.ui.screens.PokemonViewModel
import com.example.pokedex.ui.screens.PokemonsUiState
import com.example.pokedex.ui.screens.components.ErrorScreen
import com.example.pokedex.ui.screens.components.LoadingScreen
import com.example.pokedex.ui.screens.pokemonDetail.views.PokemonDetailCard

object PokemonDetail {

    private const val RouteBase = "PokemonDetail"
    private const val PokemonIdArgument = "pokemonId"
    const val Route = "$RouteBase/{$PokemonIdArgument}"
    var Title: MutableState<String> = mutableStateOf("")


    fun NavGraphBuilder.pokemonDetailNavigationEntry(
        pokemonViewModel: PokemonViewModel,
        setTitle: (String) -> Unit,

        ) {
        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) },
            arguments = listOf(
                navArgument(name = PokemonIdArgument) {
                    type = NavType.IntType
                }
            )
        ) {
            val pokemonId = it.arguments?.getInt(PokemonIdArgument) ?: 1
            Screen(
                pokemonId = pokemonId,
                pokemonViewModel = pokemonViewModel,
                retryAction = { pokemonViewModel.getPokemon(pokemonId) },
                pokemonUiState = pokemonViewModel.mPokemonUiState,
                setTitle = setTitle,

            )
        }
    }

    @Composable
    fun Screen(
        pokemonId: Int,
        pokemonViewModel: PokemonViewModel,
        pokemonUiState: PokemonsUiState,
        retryAction: () -> Unit,
        setTitle: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LaunchedEffect(Unit) {
            pokemonViewModel.getPokemon(pokemonId)

        }

        when (pokemonUiState) {
            is PokemonsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is PokemonsUiState.PokemonDetailsSuccess -> {
                Title.value = stringResource(id = R.string.pokemondetail_title, "${pokemonUiState.pokemon.name}")
                setTitle(Title.value)
                PokemonDetailCard(
                    pokemon = pokemonUiState.pokemon,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(40.dp)
                )
            }

            is PokemonsUiState.Error -> ErrorScreen(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )

            else -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }
    }

    fun NavHostController.navigateToPokemonDetail(
        pokemonId: Int,
    ) {
        navigate("$RouteBase/$pokemonId")
    }

}

