package com.tsu.itindr.request.profile

import com.tsu.itindr.request.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFeedController {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    fun feedUser(accessToken:String, onSuccess: (data: List<ProfileResponses>) -> Unit, onFailure: () -> Unit) {
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