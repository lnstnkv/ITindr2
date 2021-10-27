package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AvatarInt {
    @POST("./v1/profile/avatar")
    fun profileEdit(@Header("Authorization")token:String,@Body avatarParams: AvatarParams): Call<ProfileResponses>
}