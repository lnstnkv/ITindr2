package com.tsu.itindr.data.avatar

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AvatarInt {
    @Multipart
    @POST("./v1/profile/avatar")
        fun upload(@Header("Authorization")token:String, @Part avatar:  MultipartBody.Part): Call<String>

    @DELETE("./v1/profile/avatar")
    fun delete(@Header("Authorization")token:String): Call<String>

}
