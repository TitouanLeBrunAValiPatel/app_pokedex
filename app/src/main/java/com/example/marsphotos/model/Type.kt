package com.example.marsphotos.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiTypes(
    val name: String,
    val image : String
)
