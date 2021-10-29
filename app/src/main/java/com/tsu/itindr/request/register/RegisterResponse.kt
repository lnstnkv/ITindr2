package com.tsu.itindr.request.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val accessToken:String,
    val accessTokenExpiredAt:String,
    val refreshToken:String,
    val refreshTokenExpiredAt:String
)
