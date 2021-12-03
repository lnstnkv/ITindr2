package com.tsu.itindr.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.chat.model.Message
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.chat.MessageController
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.room.chat.ChatRepository
import kotlinx.coroutines.launch

class ChatMessageViewModel(app: Application) : AndroidViewModel(app) {
    private val chatRepository = ChatRepository(app)
    val sharedPreference = SharedPreference(app)
    val accessToken = sharedPreference.getValueString("accessToken")
    val chats = chatRepository.observeAllProfiles()
    private val cotroller = MessageController()


    fun getMessage(fName: String) {
        if (fName != null) {
            cotroller.getChat(
                "Bearer " + accessToken, fName, 50, 0,
                onSuccess = {

                    val peopleItems = mutableListOf<Message>()
                    for (getChat in it) {
                        peopleItems.add(
                            Message(
                                id = getChat.id,
                                text = getChat.text,
                                createdAt = getChat.createdAt,
                                attachments = getChat.attachments,
                                userId = getChat.user.userId,
                                name = getChat.user.name,
                                avatar = getChat.user.avatar,
                                aboutMyself = getChat.user.aboutMyself
                            )
                        )

                    }

                },
                onFailure = {

                }
            )
        }
    }
}