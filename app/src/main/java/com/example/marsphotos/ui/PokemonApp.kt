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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.marsphotos.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.data.sharedPreferences.PokemonFavoriteManager
import com.example.marsphotos.ui.screens.PokemonViewModel
import com.example.marsphotos.ui.screens.components.BottomBar
import com.example.marsphotos.ui.screens.components.PokemonTopAppBar
import com.example.marsphotos.ui.screens.home.Home
import com.example.marsphotos.ui.screens.pokemonDetail.PokemonDetail
import com.example.marsphotos.ui.screens.pokemonDetail.PokemonDetail.navigateToPokemonDetail
import com.example.marsphotos.ui.screens.pokemonDetail.PokemonDetail.pokemonDetailNavigationEntry
import com.example.marsphotos.ui.screens.pokemonFavorite.PokemonFavorite
import com.example.marsphotos.ui.screens.pokemonFavorite.PokemonFavorite.pokemonsFavoriteNavigationEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonApp(){
    val navController = rememberNavController()

    var currentScreenTitle by rememberSaveable { mutableStateOf("Home") }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PokemonTopAppBar(
            currentScreen = currentScreenTitle,
            scrollBehavior = scrollBehavior,
            navigateBack = { navController.popBackStack() }
        ) },
        bottomBar = { BottomBar(
            navToFavorite = { navController.navigate(route = PokemonFavorite.Route) },
            navToHome = { navController.navigate(route = Home.Route) }

        ) }
    ) { innerPadding ->
        val pokemonViewModel: PokemonViewModel =
            viewModel(factory = PokemonViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = Home.Route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Home.Route) {
                Home.Screen(
                    pokemonsUiState = pokemonViewModel.mPokemonsUiState,
                    searchText = pokemonViewModel.mSearchText,
                    retryAction = pokemonViewModel::getPokemons,
                    contentPadding = innerPadding,
                    setText = { pokemonViewModel.mSearchText = it } ,
                    listPokemon = pokemonViewModel.mPokemonsFilter,
                    setTitle = { currentScreenTitle = it },
                    setListPokemon = pokemonViewModel::filterListPokemon,
                    onPokemonClicked = { navController.navigateToPokemonDetail(it) }
                    )
            }

            pokemonDetailNavigationEntry(
                pokemonViewModel = pokemonViewModel,
                setTitle = { currentScreenTitle = it }
            )

            pokemonsFavoriteNavigationEntry(
                onPokemonClicked = { navController.navigateToPokemonDetail(it) },
                setTitle = { currentScreenTitle = it } ,
                pokemonViewModel = pokemonViewModel
            )

        }
    }
}


