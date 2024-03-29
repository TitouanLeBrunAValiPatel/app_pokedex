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
package com.example.pokedex.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokedex.PokemonApplication
import com.example.pokedex.data.PokemonsRepository
import com.example.pokedex.data.sharedPreferences.PokemonFavoriteManager
import com.example.pokedex.model.Pokemon
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface PokemonsUiState {
    data class Success(val pokemons: List<Pokemon>) : PokemonsUiState
    data class PokemonDetailsSuccess(val pokemon: Pokemon) : PokemonsUiState

    object Error : PokemonsUiState
    object Loading : PokemonsUiState
}

class PokemonViewModel(
    private val pokemonsRepository: PokemonsRepository,
    private val pokemonFavoriteManager: PokemonFavoriteManager
) : ViewModel() {
    var mPokemonsUiState: PokemonsUiState by mutableStateOf(PokemonsUiState.Loading)
        private set
    var mPokemonsFavoriteUiState: PokemonsUiState by mutableStateOf(PokemonsUiState.Loading)
        private set

    var mPokemonUiState: PokemonsUiState by mutableStateOf(PokemonsUiState.Loading)
        private set
    var mPokemons: List<Pokemon> by mutableStateOf(listOf())
        private set

    var mPokemonsFavorite: List<Pokemon> by mutableStateOf(listOf())
        private set
    var mPokemonsFilter: List<Pokemon> by mutableStateOf(mPokemons)
        private set

    var mSearchText: String by mutableStateOf("")
    var mPokemon: Pokemon? by mutableStateOf(null)
        private set

    init {
        getPokemons()
    }
    fun getPokemons() {
        viewModelScope.launch {
            mPokemonsUiState = PokemonsUiState.Loading
            mPokemonsUiState = try {
                mPokemons = pokemonsRepository.getPokemons()

                PokemonsUiState.Success(
                    mPokemons
                )
            } catch (e: IOException) {
                PokemonsUiState.Error
            } catch (e: HttpException) {
                PokemonsUiState.Error
            }
            filterListPokemon()

        }
    }

    fun filterListPokemon() {
        mPokemonsFilter =
            if(mSearchText.isBlank()) {
                mPokemons
            } else {
                mPokemons.filter {
                        pokemon -> pokemon.name.contains(mSearchText, ignoreCase = true)
                }
            }

}

    fun getPokemon(idPokemon: Int) {
        viewModelScope.launch {
            mPokemonUiState = PokemonsUiState.Loading
            mPokemonUiState = try {
                mPokemon = pokemonsRepository.getPokemon(idPokemon)
                PokemonsUiState.PokemonDetailsSuccess(
                    mPokemon!!
                )
            } catch (e: IOException) {
                PokemonsUiState.Error
            } catch (e: HttpException) {
                PokemonsUiState.Error
            }
        }
    }

    fun getFavoritePokemon() {
        viewModelScope.launch {
            mPokemonsFavoriteUiState = PokemonsUiState.Loading
            mPokemonsFavoriteUiState = try {
                mPokemonsFavorite = pokemonFavoriteManager.getPokemonsFavorite(pokemonsRepository)!!
                PokemonsUiState.Success(
                    mPokemonsFavorite
                )
            } catch (e: IOException) {
                PokemonsUiState.Error
            } catch (e: HttpException) {
                PokemonsUiState.Error
            }
        }

    }

    /**
     * Factory for [PokemonViewModel] that takes [PokemonsRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonApplication)
                val pokemonsRepository = application.container.pokemonsRepository
                val pokemonFavoriteManager = application.container.providePokemonFavoriteManager(application.applicationContext)
                PokemonViewModel(pokemonsRepository = pokemonsRepository,
                    pokemonFavoriteManager = pokemonFavoriteManager
                )
            }
        }
    }
}
