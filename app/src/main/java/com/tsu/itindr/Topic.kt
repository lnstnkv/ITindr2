package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id:String,
    val title:String
)
