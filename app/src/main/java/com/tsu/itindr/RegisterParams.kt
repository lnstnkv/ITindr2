package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class RegisterParams(
    val email:String,
    val password:String

)
