package com.tsu.itindr.request.chat

import com.tsu.itindr.request.user.UserParams
import kotlinx.serialization.Serializable

@Serializable
data class LastMessage(
    val id:String,
    val text:String?=null,
    val createdAt:String,
    val user:UserParams,
    val attachments:List<String>?=null
)
