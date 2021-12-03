package com.tsu.itindr.data.profile

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicController(context: Context) {
    private val api: TopicInt = Network.retrofit.create(TopicInt::class.java)
    private val sharedPreference = SharedPreference(context)
    fun topic(onSuccess: (data: List<TopicResponse>) -> Unit, onFailure: () -> Unit) {
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.getTopic(accessToken).enqueue(object : Callback<List<TopicResponse>> {
            override fun onResponse(
                call: Call<List<TopicResponse>>,
                response: Response<List<TopicResponse>>
            ) {
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
