package com.tsu.itindr

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController {
    private val api: ProfileInt = Network.retrofit.create(ProfileInt::class.java)
    fun profile(accessToken:String,onSuccess: () -> Unit, onFailure: () -> Unit) {
        api.loginProfile(accessToken).enqueue(object : Callback<ProfileResponses> {
            override fun onResponse(
                call: Call<ProfileResponses>,
                response: Response<ProfileResponses>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<ProfileResponses>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}
