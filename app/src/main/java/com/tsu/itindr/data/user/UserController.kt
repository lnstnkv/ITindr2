package com.tsu.itindr.data.user

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileResponses
import com.tsu.itindr.data.profile.UpdateParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController(context: Context) {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    private val sharedPreference = SharedPreference(context)
    fun update(updateParams: UpdateParams, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.updateProfile(accessToken, updateParams).enqueue(object : Callback<ProfileResponses> {
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
                t.printStackTrace()
                onFailure.invoke()
            }
        })
    }

}