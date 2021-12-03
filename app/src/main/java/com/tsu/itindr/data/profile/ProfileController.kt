package com.tsu.itindr.data.profile

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController(context: Context) {
    private val api: ProfileInt = Network.retrofit.create(ProfileInt::class.java)
    private val sharedPreference = SharedPreference(context)
    fun profile( onSuccess: (data: ProfileResponses) -> Unit, onFailure: () -> Unit) {
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
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
