package com.example.pokedex.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.pokedex.data.PokemonsRepository
import com.example.pokedex.model.Pokemon

class PokemonFavoriteManager(context: Context) {
    private val PREFS_NAME = "FavoriteSession"
    private val KEY_POKEMON_FAVORITE = "FavPokemon"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun clearCart() {
        editor.clear()
        editor.apply()

    }

    fun addPokemon(pokemonId: Int) {
        val cartSet = sharedPreferences.getStringSet(KEY_POKEMON_FAVORITE, HashSet())?.toMutableSet()
        cartSet?.add("$pokemonId")
        editor.putStringSet(KEY_POKEMON_FAVORITE, cartSet)
        editor.apply()

    }

    fun removePokemon(pokemonId: Int) {
        val cartSet = sharedPreferences.getStringSet(KEY_POKEMON_FAVORITE, HashSet())?.toMutableSet()
        cartSet?.remove("$pokemonId")
        editor.putStringSet(KEY_POKEMON_FAVORITE, cartSet)
        editor.apply()

    }





    suspend fun getPokemonsFavorite(pokemonsRepository: PokemonsRepository): List<Pokemon>? {
        val listIdPokemonInFavorite = sharedPreferences.getStringSet(KEY_POKEMON_FAVORITE, HashSet())?.toList() ?: emptyList()
        val pokemonsFavorite: MutableList<Pokemon> = ArrayList()



        for (pokemonId in listIdPokemonInFavorite) {
            try {
                val pokemon : Pokemon = pokemonsRepository.getPokemon(pokemonId.toInt())
                pokemonsFavorite.add(pokemon)
            } catch (e: Exception) {
                clearCart()
            }
        }

        return pokemonsFavorite
    }

    fun isFavoritePokemon(pokemonId: Int): Boolean {
        val listIdPokemonInFavorite = sharedPreferences.getStringSet(KEY_POKEMON_FAVORITE, HashSet()).toString()
        return listIdPokemonInFavorite.contains("$pokemonId")

    }

}