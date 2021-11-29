package com.tsu.itindr.room.topic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.Database
import com.tsu.itindr.room.people.ProfileEntity

class TopicRepository(context: Context) {
    private val topicDao = Database.getInstance(context).getTopicDao()

    fun observeAllProfiles(): LiveData<List<TopicItem>> =
        topicDao.observeAllTopic().map { list -> list.map { it.toDomain() } }


    suspend fun addNew(id: String, title: String) {
        topicDao.addTopic(
            TopicEntity(
                id = id,
                title = title,
            )
        )
    }
}