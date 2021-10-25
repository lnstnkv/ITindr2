package com.tsu.itindr

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvatarController {
    private val api: AvatarInt = Network.retrofit.create(AvatarInt::class.java)
    fun saveAvatar(avatarParams: AvatarParams,onSuccess: () -> Unit, onFailure: () -> Unit) {
        api.profileEdit(avatarParams).enqueue(object :
        Callback<ProfileResponses> {
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