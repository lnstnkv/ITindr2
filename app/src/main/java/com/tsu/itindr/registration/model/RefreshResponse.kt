package com.tsu.itindr.registration.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
)