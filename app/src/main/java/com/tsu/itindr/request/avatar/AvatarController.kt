package com.tsu.itindr.request.avatar

import com.tsu.itindr.request.Network
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvatarController {
    private val api: AvatarInt = Network.retrofitMultipart.create(AvatarInt::class.java)
    fun updateAvatar(
        accessToken: String,
        avatar: MultipartBody.Part,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.upload(accessToken, avatar).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }

    fun deleteAvatar(
        accessToken: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        api.delete(accessToken).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}

