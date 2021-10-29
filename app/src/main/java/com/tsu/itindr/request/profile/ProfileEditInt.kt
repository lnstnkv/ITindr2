package com.tsu.itindr.request.profile

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ProfileEditInt {
    @PATCH("./v1/profile")
    fun profileEdit(@Body profileParams: ProfileParams): Call<ProfileResponses>
}