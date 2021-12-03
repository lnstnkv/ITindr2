package com.tsu.itindr.data.profile


import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.data.user.UserInt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeController(context: Context) {
   private val api: UserInt = Network.retrofit.create(UserInt::class.java)
    private val sharedPreference = SharedPreference(context)
    fun likeUser( url:String, onSuccess: (data: LikeResponse) -> Unit, onFailure: () -> Unit){
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
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


    fun dislikeUser( url:String, onSuccess: () -> Unit, onFailure: () -> Unit){
        val accessToken = "Bearer " + sharedPreference.getValueString("accessToken").toString()
        api.disLike(accessToken, "./v1/user/" + url + "/dislike").enqueue(object : Callback<String>{
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

