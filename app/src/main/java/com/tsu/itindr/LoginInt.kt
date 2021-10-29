package com.tsu.itindr

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MultipartBody

import okhttp3.RequestBody

import okhttp3.ResponseBody

import retrofit2.http.Multipart
import retrofit2.http.Part


interface LoginInt {
    @POST("./v1/auth/login")
    fun loginProfile(@Body loginParams: LoginParams): Call<LoginResponse>
}
