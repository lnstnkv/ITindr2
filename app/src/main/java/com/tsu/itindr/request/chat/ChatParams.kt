package com.tsu.itindr.request.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatParams(
    val userId:String
)
