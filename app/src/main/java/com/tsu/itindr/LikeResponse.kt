package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual:String
)
