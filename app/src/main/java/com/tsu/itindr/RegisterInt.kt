package com.tsu.itindr

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInt {
    @POST ("./v1/auth/register")
    fun registerProfile(@Body registerParams: RegisterParams){

    }
}