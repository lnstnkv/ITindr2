package com.tsu.itindr.data.chat

import com.tsu.itindr.data.user.UserParams
import kotlinx.serialization.Serializable

@Serializable
data class LastMessage(
    val id:String,
    val text:String?=null,
    val createdAt:String,
    val user:UserParams,
    val attachments:List<String>?=null
)
