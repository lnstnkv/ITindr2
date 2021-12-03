package com.tsu.itindr.data.user

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.profile.ProfileResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFeedController(context: Context) {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    private val sharedPreference = SharedPreference(context)
    fun feedUser( onSuccess: (data: List<ProfileResponses>) -> Unit, onFailure: () -> Unit) {
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.feedUser(accessToken).enqueue(object : Callback<List<ProfileResponses>> {
            override fun onResponse(
                call: Call<List<ProfileResponses>>,
                response: Response<List<ProfileResponses>>
            )    {
                if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess.invoke(it)
                }
            } else {
                onFailure.invoke()
            }
        }
            override fun onFailure(call: Call<List<ProfileResponses>>, t: Throwable) {
                onFailure.invoke()
            }


        })
    }

}