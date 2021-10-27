package com.tsu.itindr

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicController {
    private val api: TopicInt = Network.retrofit.create(TopicInt::class.java)
    fun topic(accessToken:String, onSuccess: (data: List<TopicResponse>) -> Unit, onFailure: () -> Unit) {
    api.getTopic(accessToken).enqueue(object:Callback<List<TopicResponse>>{
        override fun onResponse(call: Call<List<TopicResponse>>, response: Response<List<TopicResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                } else {
                    onFailure.invoke()
                }
            }
        override fun onFailure(call: Call<List<TopicResponse>>, t: Throwable) {

                onFailure.invoke()

        }
    })
    }
}
