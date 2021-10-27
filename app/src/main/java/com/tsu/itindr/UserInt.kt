package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserInt {
    @PATCH("./v1/profile")
    fun updateProfile(@Header("Authorization")token:String,profileParams: ProfileParams): Call<ProfileResponses>
}