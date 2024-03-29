package com.example.pokedex.ui.screens.pokemonFavorite.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.ui.screens.components.PokemonCard
import com.example.pokedex.ui.screens.pokemonFavorite.PokemonFavorite

@Composable
fun PokemonsGridFavoriteScreen(
    onPokemonClicked: (Int) -> Unit,
    reloadPokemonFavorite : () -> Unit,
    listPokemon: List<Pokemon>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    PokemonFavorite.Title.value = stringResource(id = R.string.pokemonfavorite_title)

    Column {

        if(listPokemon.isNullOrEmpty()) {
            Text(text = stringResource(id = R.string.no_favorite_pokemon))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = modifier.padding(10.dp),
                contentPadding = contentPadding,
            ) {
                items(items = listPokemon,
                    key = { pokemon -> pokemon.id }) { pokemon ->
                    PokemonCard(
                        reloadPokemonFavorite = reloadPokemonFavorite,
                        onPokemonClicked = onPokemonClicked,
                        pokemon = pokemon,
                        modifier = modifier
                            .padding(10.dp)
                            .fillMaxSize()
                        )
                }
            }
        }

    }

}