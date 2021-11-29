package com.tsu.itindr.data.profile

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual:Boolean
)
