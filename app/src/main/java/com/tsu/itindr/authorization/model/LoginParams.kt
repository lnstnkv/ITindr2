package com.tsu.itindr.authorization.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
    val email:String,
    val password:String
)