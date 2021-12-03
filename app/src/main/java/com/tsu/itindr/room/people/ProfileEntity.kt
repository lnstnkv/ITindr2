package com.tsu.itindr.room.people

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.profile.TopicResponse
import com.tsu.itindr.find.people.model.PeopleProfile
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.stream.Collectors

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val about:String,
    val avatar: String,
    val topics: String
){
    fun toDomain()=ProfileResponses(
        userId=id,
        name= name,
        avatar=avatar,
        aboutMyself = about,
        topics = Json.decodeFromString(topics)

    )
}


