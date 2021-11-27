package com.tsu.itindr.find.chat.model

data class ProfileItem(
    val id:String,
    var username:String,
    val lastMessage:String?=null,
    val avatar:String?=null
)
