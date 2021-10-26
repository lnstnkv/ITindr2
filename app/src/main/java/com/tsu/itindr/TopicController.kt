package com.tsu.itindr

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicController {
    private val api: TopicInt = Network.retrofit.create(TopicInt::class.java)
    fun topic(accessToken:String,onSuccess: () -> Unit, onFailure: () -> Unit) {
    api.getTopic(accessToken).enqueue(object:Callback<TopicResponse>{
        override fun onResponse(call: Call<TopicResponse>, response: Response<TopicResponse>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }
        override fun onFailure(call: Call<TopicResponse>, t: Throwable) {

                onFailure.invoke()

        }
    })
    }
}