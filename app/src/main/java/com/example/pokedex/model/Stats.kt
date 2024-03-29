package com.example.pokedex.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    @SerialName(value = "HP") val hp: Int,
    val attack : Int,
    val defense : Int,
    @SerialName(value = "special_attack") val specialAttack: Int,
    @SerialName(value = "special_defense") val specialDefense: Int,
    val speed : Int
)
