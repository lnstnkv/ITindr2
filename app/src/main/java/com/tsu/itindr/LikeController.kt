package com.tsu.itindr


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*
class LikeController {
 /*   private val api: UserInt = Network.retrofitMultipart.create(UserInt::class.java)
    fun likeUser(accessToken:String, url:String, onSuccess: () -> Unit, onFailure: () -> Unit){
        api.like(accessToken,"./v1/user"++"/like").enqueue(object : Callback<LikeResponse>{
            override fun onResponse(call: Call<LikeResponse>, response: Response<LikeResponse>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}

 */