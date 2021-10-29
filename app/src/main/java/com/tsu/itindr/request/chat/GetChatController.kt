package com.tsu.itindr.request.chat

import com.tsu.itindr.request.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetChatController {
    private val api: ChatInt = Network.retrofit.create(ChatInt::class.java)
    fun getChat(accessToken:String, onSuccess: (data:List<GetChatResponse>) -> Unit, onFailure: () -> Unit){
        api.getChat(accessToken).enqueue(object :Callback<List<GetChatResponse>>{
            override fun onResponse(
                call: Call<List<GetChatResponse>>,
                response: Response<List<GetChatResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let{
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