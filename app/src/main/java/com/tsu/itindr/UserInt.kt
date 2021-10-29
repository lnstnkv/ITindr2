package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.*

interface UserInt {
    @PATCH("./v1/profile")
    fun updateProfile(@Header("Authorization")token:String,@Body updateParams: UpdateParams): Call<ProfileResponses>

    @GET("./v1/user/feed")
    fun feedUser(@Header("Authorization")token:String):Call<List<ProfileResponses>>

    @POST()
    fun like(@Header("Authorization")token:String,@Url url:String):Call<LikeResponse>
}