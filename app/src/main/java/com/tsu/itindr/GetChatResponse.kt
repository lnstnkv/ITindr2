package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class GetChatResponse (
    val chat:ChatResponse,
    val lastMessage: LastMessage,
    val user:UserParams,
    val attachments:List<String>
    )