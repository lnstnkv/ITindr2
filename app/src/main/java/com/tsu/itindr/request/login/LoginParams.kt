package com.tsu.itindr.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginParams(
    val email:String,
    val password:String
)