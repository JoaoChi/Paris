package com.angellira.paris.model

import kotlinx.serialization.Serializable

@Serializable
data class MarsPhoto(
    val id: List<MarsPhoto>,
    val img_src: String
    )