package com.tsu.itindr.data.chat

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.user.UserInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

class MessageController()  {
    private val api: ChatInt = Network.retrofit.create(ChatInt::class.java)
    //private val sharedPreference = SharedPreference(context)
    fun getChat(
        accessToken:String,
        chatId: String,
        limit: Int,
        offset: Int,
        onSuccess: (data: List<MessegeResponse>) -> Unit,
        onFailure: () -> Unit
    ) {
       // val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.getChatMessage(accessToken, chatId, limit, offset)
            .enqueue(object : Callback<List<MessegeResponse>> {
                override fun onResponse(
                    call: Call<List<MessegeResponse>>,
                    response: Response<List<MessegeResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess.invoke(it)
                        }
                    } else {
                        onFailure.invoke()
                    }
                }

                override fun onFailure(call: Call<List<MessegeResponse>>, t: Throwable) {
                    t.printStackTrace()
                    onFailure.invoke()
                }
            })
    }

}