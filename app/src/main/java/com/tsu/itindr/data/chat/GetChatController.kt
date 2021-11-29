package com.tsu.itindr.data.chat

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetChatController(context: Context) {
    private val api: ChatInt = Network.retrofit.create(ChatInt::class.java)
    private val sharedPreference = SharedPreference(context)
    val accessToken = sharedPreference.getValueString("accessToken")

    fun getChat(onSuccess: (data: List<GetChatResponse>) -> Unit, onFailure: () -> Unit) {
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.getChat(accessToken).enqueue(object : Callback<List<GetChatResponse>> {

            override fun onResponse(
                call: Call<List<GetChatResponse>>,
                response: Response<List<GetChatResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<List<GetChatResponse>>, t: Throwable) {
                t.printStackTrace()
                onFailure.invoke()
            }
        })
    }
}