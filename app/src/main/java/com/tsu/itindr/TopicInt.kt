package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface TopicInt {
    @GET("./v1/topic")
    fun getTopic(@Header("Authorization")token:String): Call<List<TopicResponse>>
}