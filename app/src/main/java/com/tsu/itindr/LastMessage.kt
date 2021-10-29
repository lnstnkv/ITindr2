package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class LastMessage(
    val id:String,
    val text:String,
    val createAt:String
)
