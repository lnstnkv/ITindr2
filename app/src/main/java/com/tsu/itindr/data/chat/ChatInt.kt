package com.tsu.itindr.data.chat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatInt {
    @POST("./v1/chat")
    fun createChat(@Header("Authorization")token:String,chatParams: ChatParams): Call<ChatResponse>

    @GET("./v1/chat")
    fun getChat(@Header("Authorization")token:String): Call<List<GetChatResponse>>

}