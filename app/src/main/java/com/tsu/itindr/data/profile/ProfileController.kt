package com.tsu.itindr.data.profile

import com.tsu.itindr.data.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController {
    private val api: ProfileInt = Network.retrofit.create(ProfileInt::class.java)
    fun profile(accessToken:String, onSuccess: (data: ProfileResponses) -> Unit, onFailure: () -> Unit) {
        api.loginProfile(accessToken).enqueue(object : Callback<ProfileResponses> {
            override fun onResponse(
                call: Call<ProfileResponses>,
                response: Response<ProfileResponses>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                    }
                }
                    else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<ProfileResponses>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}
