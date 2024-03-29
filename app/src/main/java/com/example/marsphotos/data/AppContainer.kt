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
package com.example.marsphotos.data

import android.content.Context
import com.example.marsphotos.data.sharedPreferences.PokemonFavoriteManager
import com.example.marsphotos.network.PokemonApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    fun providePokemonFavoriteManager(context: Context): PokemonFavoriteManager
    val pokemonsRepository: PokemonsRepository
}
class DefaultAppContainer() : AppContainer {
    private val baseUrl = "https://pokebuildapi.fr/api/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    override val pokemonsRepository: PokemonsRepository by lazy {
        NetworkPokemonsRepository(retrofitService)
    }

    override fun providePokemonFavoriteManager(context: Context): PokemonFavoriteManager {
        return PokemonFavoriteManager(context)
    }}
