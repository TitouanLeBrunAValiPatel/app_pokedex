package com.example.marsphotos.ui.screens.home.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R
import com.example.marsphotos.data.sharedPreferences.PokemonFavoriteManager
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.screens.components.PokemonCard
import com.example.marsphotos.ui.screens.home.Home

@Composable
fun PokemonsGridScreen(
    onPokemonClicked: (Int) -> Unit,
    setText: (String) -> Unit,
    setListPokemon: () -> Unit,
    listPokemon: List<Pokemon>,
    searchText: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) { 
    Home.Title.value = stringResource(id = R.string.home_title)
    Column {
        TextField(value = searchText,
            onValueChange = {
                setText(it)
                setListPokemon()
            },
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_pokemon_bar)
                )
            })

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.padding(10.dp),
            contentPadding = contentPadding,
        ) {
            items(items = listPokemon,
                key = { pokemon -> pokemon.id }) { pokemon ->
                PokemonCard(
                    onPokemonClicked = onPokemonClicked, pokemon = pokemon,
                    modifier = modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                )
            }
        }
    }

}