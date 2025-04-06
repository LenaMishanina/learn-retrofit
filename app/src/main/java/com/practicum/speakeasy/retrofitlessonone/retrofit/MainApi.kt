package com.practicum.speakeasy.retrofitlessonone.retrofit

import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.AuthRequest
import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.User
import com.practicum.speakeasy.retrofitlessonone.retrofit.post.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApi {
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User
}