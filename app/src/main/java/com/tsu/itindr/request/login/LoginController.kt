package com.tsu.itindr.request.login

import com.tsu.itindr.request.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController {
    private val api: LoginInt = Network.retrofit.create(LoginInt::class.java)

    fun login(loginParams: LoginParams, onSuccess: (data: LoginResponse) -> Unit, onFailure: () -> Unit) {
        api.loginProfile(loginParams).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let{
                    onSuccess.invoke(it)
                    }
                } else {
                    onFailure.invoke()
                    //Log.i(TAG, "Ошибка тут")
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}