package com.tsu.itindr.registration.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterParams(
    val email:String,
    val password:String
)
