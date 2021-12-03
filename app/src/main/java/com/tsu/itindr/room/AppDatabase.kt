package com.tsu.itindr.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tsu.itindr.room.chat.ChatDao
import com.tsu.itindr.room.chat.ChatEntity
import com.tsu.itindr.room.people.ProfileDao
import com.tsu.itindr.room.people.ProfileEntity
import com.tsu.itindr.room.topic.TopicDao
import com.tsu.itindr.room.topic.TopicEntity

@Database(entities = [ProfileEntity::class,TopicEntity::class,ChatEntity::class], version = 5)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getProfileDao(): ProfileDao

    abstract  fun getTopicDao(): TopicDao

    abstract  fun getChatDao(): ChatDao
}

