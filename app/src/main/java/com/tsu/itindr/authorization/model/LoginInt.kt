package com.tsu.itindr.authorization.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginInt {
    @POST("./v1/auth/login")
    fun loginProfile(@Body loginParams: LoginParams): Call<LoginResponse>
}
