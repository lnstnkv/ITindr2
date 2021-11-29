package com.tsu.itindr.data.profile

import com.tsu.itindr.data.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileEditController {
    private val api: ProfileEditInt = Network.retrofit.create(ProfileEditInt::class.java)
    fun profile(profileParams: ProfileParams, onSuccess: () -> Unit, onFailure: () -> Unit) {
        api.profileEdit(profileParams).enqueue(object : Callback<ProfileResponses> {
            override fun onResponse(
                call: Call<ProfileResponses>,
                response: Response<ProfileResponses>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                    //Log.i(TAG, "Ошибка тут")
                }
            }

            override fun onFailure(call: Call<ProfileResponses>, t: Throwable) {
                onFailure.invoke()
            }


        })
    }
}