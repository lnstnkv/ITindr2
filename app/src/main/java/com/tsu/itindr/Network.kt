package com.tsu.itindr

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Network {

    private  const val BASE_URL=" http://193.38.50.175/itindr/api/mobile"
    private val mediaType= "application/json".toMediaType()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor()).build()

    @ExperimentalSerializationApi
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL).addConverterFactory(Json.asConverterFactory(mediaType)).build()
}