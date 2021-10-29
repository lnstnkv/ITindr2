package com.tsu.itindr

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse (
    val id:String,
    val title:String,
    val avatar:String?=null
    )