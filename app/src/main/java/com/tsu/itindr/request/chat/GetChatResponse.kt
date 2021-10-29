package com.tsu.itindr.request.chat

import com.tsu.itindr.request.profile.UserParams
import kotlinx.serialization.Serializable

@Serializable
data class GetChatResponse (
    val chat: ChatResponse,
    val lastMessage: LastMessage,
    val user: UserParams,
    val attachments:List<String>
    )