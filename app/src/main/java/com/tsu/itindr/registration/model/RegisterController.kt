package com.tsu.itindr.registration.model

import android.content.Context
import com.tsu.itindr.data.Network
import com.tsu.itindr.data.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterController(context: Context) {

    private val api: RegisterInt = Network.retrofit.create(RegisterInt::class.java)

    private val sharedPreference = SharedPreference(context)

    fun register(
        registerParams: RegisterParams,
        onSuccess: (data: RegisterResponse) -> Unit,
        onFailure: () -> Unit
    ) {
        api.registerProfile(registerParams).enqueue(object : Callback<RegisterResponse>
        {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess.invoke(it)
                        sharedPreference.save("accessToken", it.accessToken)

                    }
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure.invoke()
            }

        })
    }
}