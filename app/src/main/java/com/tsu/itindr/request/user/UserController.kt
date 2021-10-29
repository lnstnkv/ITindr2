package com.tsu.itindr.request.user

import com.tsu.itindr.request.Network
import com.tsu.itindr.request.profile.ProfileResponses
import com.tsu.itindr.request.profile.UpdateParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    fun update(accessToken:String, updateParams: UpdateParams, onSuccess: () -> Unit, onFailure: () -> Unit){
        api.updateProfile(accessToken,updateParams).enqueue(object: Callback<ProfileResponses>{
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