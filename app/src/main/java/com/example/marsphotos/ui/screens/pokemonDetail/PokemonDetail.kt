package com.example.marsphotos.ui.screens.pokemonDetail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotos.R
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.screens.PokemonViewModel
import com.example.marsphotos.ui.screens.PokemonsUiState
import com.example.marsphotos.ui.screens.components.ErrorScreen
import com.example.marsphotos.ui.screens.components.LoadingScreen

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    pokemonViewModel: PokemonViewModel,
    pokemonUiState: PokemonsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        pokemonViewModel.getPokemon(pokemonId)
    }

    when (pokemonUiState) {
        is PokemonsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PokemonsUiState.PokemonDetailsSuccess -> PokemonDetailCard(pokemon = pokemonUiState.pokemon,
            modifier = modifier
                .fillMaxSize()
                .padding(40.dp)
        )
        is PokemonsUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
        else -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())
    }
}
fun NavHostController.navigateToAnimalDetail(
    animalId: Int,
) {
    navigate("$RouteBase?$AnimalArgument=$animalId")
}


/**
 * The home screen displaying error message with re-attempt button.
 */

@Composable
fun PokemonDetailCard(pokemon: Pokemon, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(pokemon.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.pokemon_photo),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = stringResource(id = R.string.pokemon_types))

        pokemon.apiTypes.forEach { type ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = type.name,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(type.image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.pokemon_type_photo),
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

