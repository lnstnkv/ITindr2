package com.tsu.itindr.data.chat

import kotlinx.serialization.Serializable

@Serializable
data class GetChatResponse (
    val chat: ChatResponse,
    val lastMessage: LastMessage?=null,
    )