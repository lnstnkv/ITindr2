package com.tsu.itindr.room.chat

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tsu.itindr.data.chat.LastMessage
import com.tsu.itindr.room.people.ProfileEntity

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChat(chatEntity: ChatEntity)

    @Update
    suspend fun updateChat(chatEntity: ChatEntity)

    @Delete
    suspend fun deleteChat(chatEntity: ChatEntity)

    @Query("UPDATE chat SET title= :title WHERE id= :id")
    suspend fun updateProfileName(id:String,title:String)

    @Query("UPDATE chat SET avatar= :avatar WHERE id= :id")
    suspend fun updateAvatar(id:String,avatar:String)

    @Query("UPDATE chat SET lastMessage= :lastMessage WHERE id= :id")
    suspend fun updateProfileLastMessage(id:String,lastMessage: String)

    @Query("SELECT * FROM chat")
    fun observeAll(): LiveData<List<ChatEntity>>
}