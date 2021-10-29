package com.tsu.itindr.request.profile


import com.tsu.itindr.request.Network
import com.tsu.itindr.request.user.UserInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeController {
   private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    fun likeUser(accessToken:String, url:String, onSuccess: (data: LikeResponse) -> Unit, onFailure: () -> Unit){
        api.like(accessToken, "./v1/user/" + url + "/like").enqueue(object : Callback<LikeResponse>{
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let{
                        onSuccess.invoke(it)
                    }
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                t.printStackTrace()
                onFailure.invoke()
            }
        })
    }


    fun dislikeUser(accessToken:String, url:String, onSuccess: () -> Unit, onFailure: () -> Unit){
        api.disLike(accessToken, "./v1/user/" + url + "/like").enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {
                    onSuccess.invoke()
                }
                else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}

