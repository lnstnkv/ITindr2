package com.tsu.itindr.data.user

import com.tsu.itindr.data.Network
import com.tsu.itindr.data.profile.ProfileResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleController {
    private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    fun getUser(accessToken:String, limit:Int, offset:Int, onSuccess: (data:List<ProfileResponses>) -> Unit, onFailure: () -> Unit){
        api.getPeople(accessToken,limit,offset).enqueue(object:Callback<List<ProfileResponses>>{
            override fun onResponse(
                call: Call<List<ProfileResponses>>,
                response: Response<List<ProfileResponses>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let{
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