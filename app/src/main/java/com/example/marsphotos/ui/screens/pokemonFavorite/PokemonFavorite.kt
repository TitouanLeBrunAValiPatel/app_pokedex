package com.example.marsphotos.ui.screens.pokemonFavorite

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.compose.composable
import com.example.marsphotos.R
import com.example.marsphotos.data.sharedPreferences.PokemonFavoriteManager
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.screens.PokemonViewModel
import com.example.marsphotos.ui.screens.PokemonsUiState
import com.example.marsphotos.ui.screens.components.ErrorScreen
import com.example.marsphotos.ui.screens.components.LoadingScreen
import com.example.marsphotos.ui.screens.pokemonDetail.PokemonDetail
import com.example.marsphotos.ui.screens.pokemonDetail.views.PokemonDetailCard
import com.example.marsphotos.ui.screens.pokemonFavorite.views.PokemonsGridFavoriteScreen

object PokemonFavorite {
    private const val RouteBase = "Favorite"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    fun NavGraphBuilder.pokemonsFavoriteNavigationEntry(
        onPokemonClicked: (Int) -> Unit,
        pokemonViewModel: PokemonViewModel,
        setTitle: (String) -> Unit
    ) {

        composable(
            route = Route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(animationSpec = tween(2000)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(animationSpec = tween(2000)) }
        ) {
            Screen(
                onPokemonClicked = onPokemonClicked,
                pokemonViewModel = pokemonViewModel,
                setTitle = setTitle,
                retryAction = { pokemonViewModel.getFavoritePokemon() },
                pokemonsFavoriteUiState = pokemonViewModel.mPokemonsFavoriteUiState,
                reloadPokemonFavorite = { pokemonViewModel.getFavoritePokemon() }
            )
        }
    }

    @Composable
    fun Screen(
        pokemonsFavoriteUiState: PokemonsUiState,
        modifier: Modifier = Modifier,
        setTitle: (String) -> Unit,
        pokemonViewModel: PokemonViewModel,
        onPokemonClicked: (Int) -> Unit,
        retryAction: () -> Unit,
        reloadPokemonFavorite : () -> Unit,
        contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {
        Title.value = stringResource(id = R.string.pokemonfavorite_title)
        setTitle(Title.value)

        LaunchedEffect(Unit) {
            pokemonViewModel.getFavoritePokemon()

        }
        when (pokemonsFavoriteUiState) {
            is PokemonsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is PokemonsUiState.Success -> PokemonsGridFavoriteScreen(
                onPokemonClicked = onPokemonClicked,
                modifier = modifier,
                contentPadding = contentPadding,
                listPokemon = pokemonsFavoriteUiState.pokemons,
                reloadPokemonFavorite = reloadPokemonFavorite

            )

            is PokemonsUiState.Error -> ErrorScreen(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )

            else -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }



    }
}
