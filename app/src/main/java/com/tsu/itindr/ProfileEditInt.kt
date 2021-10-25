package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProfileEditInt {
    @PATCH("./v1/profile")
    fun profileEdit(@Body profileParams: ProfileParams): Call<ProfileResponses>
}