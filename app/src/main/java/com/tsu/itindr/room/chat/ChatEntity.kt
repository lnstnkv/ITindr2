package com.tsu.itindr.room.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.itindr.data.TopicItem
import com.tsu.itindr.data.chat.LastMessage

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val avatar:String,
    val lastMessage: String
) {
    fun toDomain() = Chat(
        id = id,
        title = title,
        avatar=avatar,
        lastMessage=lastMessage

    )
}