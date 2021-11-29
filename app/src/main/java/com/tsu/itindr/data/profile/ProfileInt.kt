package com.tsu.itindr.data.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface ProfileInt {
    @GET("./v1/profile")
    fun loginProfile(@Header("Authorization")token:String): Call<ProfileResponses>

}