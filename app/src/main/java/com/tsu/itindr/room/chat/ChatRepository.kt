package com.tsu.itindr.room.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.data.chat.LastMessage
import com.tsu.itindr.edit.model.Avatar
import com.tsu.itindr.room.Database
import com.tsu.itindr.room.topic.TopicEntity

class ChatRepository (context: Context){
    private val chatDao = Database.getInstance(context).getChatDao()

    fun observeAllProfiles(): LiveData<List<Chat>> =
        chatDao.observeAll().map { list -> list.map { it.toDomain() } }


    suspend fun addNew(id: String, title: String,avatar: String, lastMessage: String) {
        chatDao.addChat(
            ChatEntity(
                id = id,
                title = title,
                avatar=avatar,
                lastMessage=lastMessage
            )
        )
    }
}