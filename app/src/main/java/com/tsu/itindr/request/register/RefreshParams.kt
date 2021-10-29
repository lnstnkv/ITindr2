package com.tsu.itindr.request.register

import kotlinx.serialization.Serializable

@Serializable
data class RefreshParams(
    val refreshToken:String,
        )