package com.angellira.paris.model

import kotlinx.serialization.Serializable

@Serializable
data class SportPhoto(
    val id: Int,
    val name: String,
    val img: String,
    val description: String
)