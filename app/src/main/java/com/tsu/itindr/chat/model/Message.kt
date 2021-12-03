package com.tsu.itindr.chat.model

data class Message(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val attachments: List<String>? = null,
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null
)