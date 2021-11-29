package com.tsu.itindr.room.topic

import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.find.people.model.PeopleProfile

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
){
    fun toDomain()= TopicItem(
        id=id,
        title=title
    )
}