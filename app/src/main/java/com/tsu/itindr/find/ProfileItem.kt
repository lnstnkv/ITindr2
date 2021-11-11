package com.tsu.itindr.find

data class ProfileItem(
    val id:String,
    var username:String,
    val lastMessage:String?=null,
    val avatar:String?=null
)
