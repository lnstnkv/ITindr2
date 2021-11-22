package com.tsu.itindr.registration.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshParams(
    val refreshToken:String,
        )