package com.tsu.itindr

import android.util.Log
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterController {

    private val api: RegisterInt = Network.retrofit.create(RegisterInt::class.java)

    fun register(registerParams: RegisterParams, onSuccess: () -> Unit, onFailure: () -> Unit) {
        api.registerProfile(registerParams).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onFailure.invoke()
                    //Log.i(TAG, "Ошибка тут")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure.invoke()
               // Log.i(TAG, "нет Ошибка тут")
            }

        })
    }
}