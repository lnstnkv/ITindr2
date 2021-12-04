package com.tsu.itindr.data

import com.tsu.itindr.data.profile.TopicResponse

data class TopicItem(
    val id: String,
    val title: String
){
    fun toDomainSerialization() = TopicResponse(
        id = id,
        title = title
    )
}