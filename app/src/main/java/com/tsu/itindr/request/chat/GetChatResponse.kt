package com.tsu.itindr.request.chat

import com.tsu.itindr.request.user.UserParams
import kotlinx.serialization.Serializable

@Serializable
data class GetChatResponse (
    val chat: ChatResponse,
    val lastMessage: LastMessage?=null,
    )