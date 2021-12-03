package com.tsu.itindr.data.chat

import com.tsu.itindr.data.profile.ProfileResponses
import retrofit2.Call
import retrofit2.http.*

interface ChatInt {
    @POST("./v1/chat")
    fun createChat(
        @Header("Authorization") token: String,
        chatParams: ChatParams
    ): Call<ChatResponse>

    @GET("./v1/chat")
    fun getChat(@Header("Authorization") token: String): Call<List<GetChatResponse>>

    @GET("v1/chat/{chatId}/message")
    fun getChatMessage(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<List<MessegeResponse>>

}