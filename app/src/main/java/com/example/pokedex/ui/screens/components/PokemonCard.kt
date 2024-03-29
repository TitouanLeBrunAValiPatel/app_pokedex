package com.example.pokedex.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.data.sharedPreferences.PokemonFavoriteManager
import com.example.pokedex.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    onPokemonClicked: (Int) -> Unit,
    reloadPokemonFavorite : () -> Unit = {},
    pokemon: Pokemon, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pokemonFavoriteManager = PokemonFavoriteManager(context)
    var isPokemonFavorite = pokemonFavoriteManager.isFavoritePokemon(pokemonId = pokemon.id)

    var isFavoriteImage by remember { mutableStateOf(isPokemonFavorite) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onPokemonClicked(pokemon.id) }
    ) {

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                Modifier.clickable(onClick = {
                    if(isPokemonFavorite) pokemonFavoriteManager.removePokemon(pokemonId = pokemon.id)
                    else pokemonFavoriteManager.addPokemon(pokemon.id)
                    reloadPokemonFavorite()
                    isFavoriteImage = !isFavoriteImage
                    })
            ) {
                var imageFavorite =
                    if(isFavoriteImage) R.drawable.ic_favorite
                    else R.drawable.ic_favorite_border

                Image(painter = painterResource(id = imageFavorite), contentDescription = stringResource(
                    id = R.string.favorite_button
                ))

            }
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pokemon.sprite)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.pokemon_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(
                text = pokemon.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )
        }
    }
}