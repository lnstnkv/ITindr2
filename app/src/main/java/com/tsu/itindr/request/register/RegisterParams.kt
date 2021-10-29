package com.tsu.itindr.request.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterParams(
    val email:String,
    val password:String
)
