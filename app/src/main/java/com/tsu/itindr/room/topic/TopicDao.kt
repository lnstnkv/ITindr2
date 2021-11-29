package com.tsu.itindr.room.topic

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopic(topicEntity: TopicEntity)

    @Update
    suspend fun updateTopic(topicEntity: TopicEntity)

    @Delete
    suspend fun deleteTopic(topicEntity: TopicEntity)

    @Query("UPDATE topic SET title= :title WHERE id= :id")
    suspend fun updateTopicName(id:String,title:String)


    @Query("SELECT * FROM topic")
    fun observeAllTopic(): LiveData<List<TopicEntity>>
}