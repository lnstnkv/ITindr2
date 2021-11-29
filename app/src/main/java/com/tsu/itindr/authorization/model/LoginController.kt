package com.tsu.itindr.authorization.model

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController(context: Context) {
    private val api: LoginInt = Network.retrofit.create(LoginInt::class.java)
    private val sharedPreference= SharedPreference(context)

    fun login(loginParams: LoginParams, onSuccess: (data: LoginResponse) -> Unit, onFailure: () -> Unit) {
        api.loginProfile(loginParams).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let{
                    onSuccess.invoke(it)
                        sharedPreference.save("accessToken", it.accessToken)
                    }
                } else {
                    onFailure.invoke()
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}