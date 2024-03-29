/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens.home

import android.icu.text.CaseMap.Title
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.example.marsphotos.R
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.screens.PokemonsUiState
import com.example.marsphotos.ui.screens.components.ErrorScreen
import com.example.marsphotos.ui.screens.components.LoadingScreen
object HomeScreen {
    private const val RouteBase = "Start"
//    private const val AnimalArgument = "animalId"
    private const val Route = "$RouteBase"
    private const val Title = "Pokedex"
    @Composable
    fun HomeScreen(
        pokemonsUiState: PokemonsUiState,
        retryAction: () -> Unit,
        searchText: String,
        setText: (String) -> Unit,
        setListPokemon: () -> Unit,
        listPokemon: List<Pokemon>,
        modifier: Modifier = Modifier,
        onPokemonClicked: (Int) -> Unit,
        contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {

        when (pokemonsUiState) {
            is PokemonsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

            is PokemonsUiState.Success -> PokemonsGridScreen(
                onPokemonClicked = onPokemonClicked,
                modifier = modifier,
                contentPadding = contentPadding,
                setListPokemon = setListPokemon,
                searchText = searchText,
                setText = setText,
                listPokemon = listPokemon,

                )

            is PokemonsUiState.Error -> ErrorScreen(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )

            else -> ErrorScreen(retryAction = retryAction, modifier = modifier.fillMaxSize())

        }
    }

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
        Column {
            TextField(value = searchText,
                onValueChange = {
                    setText(it)
                    setListPokemon()
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search a pokemon by name"
                    )
                })

            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                modifier = modifier.padding(10.dp),
                contentPadding = contentPadding,
            ) {
                items(items = listPokemon,
                    key = { pokemon -> pokemon.id }) { pokemon ->
                    PokemonCard(
                        onPokemonClicked = onPokemonClicked, pokemon = pokemon,
                        modifier = modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PokemonCard(
        onPokemonClicked: (Int) -> Unit,
        pokemon: Pokemon, modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            onClick = { onPokemonClicked(pokemon.id) }
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(pokemon.imgSrc)
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
}