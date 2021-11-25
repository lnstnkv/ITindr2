package com.tsu.itindr

import okhttp3.Interceptor
import okhttp3.Response
import java.net.UnknownHostException
import kotlin.Exception

class StatusCodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            handleResponse(chain.proceed(request))
        } catch (e: UnknownHostException) {
            throw Exception("fdkfdg")
        }

    }

    private fun handleResponse(response: Response): Response {
        when (response.code) {
            in 200..300->Unit
            401-> Unit
            else->throw Exception(response.message)
        }
        return response
    }
}