package com.tsu.itindr.find.people.model

import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.room.people.ProfileEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class PeopleProfile(
    val id: String,
    val username: String? = null,
    val about: String? = null,
    val avatar: String? = null,
    val topics: List<TopicItem>
)
