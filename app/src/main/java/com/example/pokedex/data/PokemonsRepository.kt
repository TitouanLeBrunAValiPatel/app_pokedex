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
package com.example.pokedex.data

import com.example.pokedex.model.Pokemon
import com.example.pokedex.network.PokemonApiService
interface PokemonsRepository {
    suspend fun getPokemons(): List<Pokemon>
    suspend fun getPokemon(idPokemon: Int): Pokemon
}
class NetworkPokemonsRepository(
    private val pokemonApiService: PokemonApiService
) : PokemonsRepository {
    override suspend fun getPokemons(): List<Pokemon> = pokemonApiService.getPokemons()
    override suspend fun getPokemon(idPokemon: Int): Pokemon = pokemonApiService.getPokemon(idPokemon)
}
