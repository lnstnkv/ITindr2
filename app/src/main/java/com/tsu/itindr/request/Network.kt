package com.tsu.itindr.request

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Network {

    private  const val BASE_URL= "http://193.38.50.175/itindr/api/mobile/"
    private val mediaType= "application/json".toMediaType()
    private val multiMediaType= "multipart/from-data".toMediaType()

    private val interceptor: HttpLoggingInterceptor by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val client = OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()

    @ExperimentalSerializationApi
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL).addConverterFactory(Json.asConverterFactory(mediaType)).build()
    @ExperimentalSerializationApi
    val retrofitMultipart: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL).addConverterFactory(Json.asConverterFactory(multiMediaType)).build()
}