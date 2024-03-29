package com.example.marsphotos.model

import kotlinx.serialization.Serializable

@Serializable
data class Type(
    val name: String,
    val image : String
)
