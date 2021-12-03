package com.tsu.itindr.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.chat.model.Message
import com.tsu.itindr.data.chat.MessageController
import com.tsu.itindr.data.chat.MessegeResponse
import com.tsu.itindr.room.chat.ChatRepository

class ChatMessageViewModel(app: Application) : AndroidViewModel(app) {
    private val chatRepository = ChatRepository(app)

    val chats = chatRepository.observeAllProfiles()
    private val cotroller = MessageController(app)

    private val _isErrorGetMessage = MutableLiveData<Boolean>()
    val isErrorGetMessage: LiveData<Boolean>
        get() = _isErrorGetMessage

    private val _getMessageItem = MutableLiveData<List<Message>>()
    val getMessageItem: LiveData<List<Message>>
        get() = _getMessageItem

    fun getMessage(fName: String) {
        cotroller.getChat(
            fName, 50, 0,
            onSuccess = {
                _isErrorGetMessage.value = false
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
                    _getMessageItem.value = peopleItems
                }

            },
            onFailure = {
                _isErrorGetMessage.value = true
            }
        )
    }
}