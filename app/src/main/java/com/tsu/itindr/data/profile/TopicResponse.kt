package com.tsu.itindr.data.profile

import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.find.people.model.PeopleProfile
import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val id: String,
    val title: String
) {
    fun toDomainTopicData() = TopicItem(
        id = id,
        title = title
    )
}
