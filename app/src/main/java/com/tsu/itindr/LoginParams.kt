package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
    val email:String,
    val password:String
)