package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class RefreshParams(
    val refreshToken:String,
        )