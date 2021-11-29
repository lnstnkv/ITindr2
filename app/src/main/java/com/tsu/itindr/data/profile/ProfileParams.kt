package com.tsu.itindr.data.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileParams (
    val name: String?=null,
    val aboutMyself: String?=null,
    val topic:List<TopicResponse>
    )