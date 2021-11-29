package com.tsu.itindr.find.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.chat.GetChatController
import com.tsu.itindr.data.chat.GetChatResponse
import com.tsu.itindr.find.chat.model.ProfileItem

class ChatViewModel(app: Application) : AndroidViewModel(app) {
    val sharedPreference = SharedPreference(app)

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    private val _isChat = MutableLiveData<MutableList<ProfileItem>?>()
    val isChat: LiveData<MutableList<ProfileItem>?>
        get() = _isChat

    private var controller = GetChatController(app)
    fun getChat() {
        controller.getChat(
            onSuccess = {
                _isError.value = false

                val profileItems = mutableListOf<ProfileItem>()
                for (getChat in it) {
                    profileItems.add(
                        ProfileItem(
                            id = getChat.chat.id,
                            username = getChat.chat.title,
                            lastMessage = getChat.lastMessage?.text,
                            avatar = getChat.chat.avatar
                        )
                    )
                }
                _isChat.value = profileItems

            },
            onFailure = {
                _isError.value = true
            }

        )
    }


}