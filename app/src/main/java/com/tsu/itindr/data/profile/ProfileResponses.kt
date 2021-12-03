package com.tsu.itindr.data.profile

import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.people.ProfileEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ProfileResponses(
    val userId: String,
    val name: String? = null,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<TopicResponse>
) {
    fun toEntityData() = ProfileEntity(
        id = userId,
        name = name ?: "",
        about = aboutMyself ?: "",
        avatar = avatar ?: "",
        topics = Json.encodeToString(topics)

    )

    fun toDomainData() = PeopleProfile(
        id = userId,
        username = name,
        avatar = avatar,
        topics = topics.map { it.toDomainTopicData() }

    )
}