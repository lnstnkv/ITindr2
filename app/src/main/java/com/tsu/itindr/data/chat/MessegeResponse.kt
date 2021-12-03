package com.tsu.itindr.data.chat

import com.tsu.itindr.data.user.UserParams
import kotlinx.serialization.Serializable

@Serializable
data class MessegeResponse(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val attachments: List<String>? = null,
    val user: UserParams
)
