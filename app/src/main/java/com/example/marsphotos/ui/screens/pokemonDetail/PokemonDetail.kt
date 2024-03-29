package com.example.marsphotos.ui.screens.pokemonDetail

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotos.R
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.screens.PokemonViewModel
import com.example.marsphotos.ui.screens.PokemonsUiState
import com.example.marsphotos.ui.screens.components.ErrorScreen
import com.example.marsphotos.ui.screens.components.LoadingScreen
import com.example.marsphotos.ui.screens.pokemonDetail.views.PokemonDetailCard

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

