package com.tsu.itindr.request.profile

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual:Boolean
)
