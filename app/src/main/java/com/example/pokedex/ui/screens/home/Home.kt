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
package com.example.pokedex.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.ui.screens.PokemonsUiState
import com.example.pokedex.ui.screens.components.ErrorScreen
import com.example.pokedex.ui.screens.components.LoadingScreen
import com.example.pokedex.ui.screens.home.views.PokemonsGridScreen

object Home {
    private const val RouteBase = "Start"
    const val Route = "$RouteBase"
    var Title: MutableState<String> = mutableStateOf("")

    @Composable
    fun Screen(
        pokemonsUiState: PokemonsUiState,
        retryAction: () -> Unit,
        searchText: String,
        setText: (String) -> Unit,
        setTitle: (String) -> Unit,
        setListPokemon: () -> Unit,
        listPokemon: List<Pokemon>,
        modifier: Modifier = Modifier,
        onPokemonClicked: (Int) -> Unit,
        contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {
        Title.value = stringResource(id = R.string.home_title)
        setTitle(Title.value)

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

}