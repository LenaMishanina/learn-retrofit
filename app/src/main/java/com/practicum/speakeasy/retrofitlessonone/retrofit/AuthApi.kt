package com.practicum.speakeasy.retrofitlessonone.retrofit

import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.AuthRequest
import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User
}