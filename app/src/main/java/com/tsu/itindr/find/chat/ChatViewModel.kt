package com.tsu.itindr.find.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.request.chat.GetChatController
import com.tsu.itindr.request.chat.GetChatResponse

class ChatViewModel(app: Application) : AndroidViewModel(app) {
    val sharedPreference = SharedPreference(app)

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    private val _isChat = MutableLiveData<List<GetChatResponse>?>()
    val isChat: LiveData<List<GetChatResponse>?>
        get() = _isChat


    private var controller = GetChatController()
    fun getChat() {
        controller.getChat(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                _isError.value = false
                _isChat.value = it

            },
            onFailure = {
                _isError.value = true
            }

        )
    }


}