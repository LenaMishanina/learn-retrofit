package com.practicum.speakeasy.retrofitlessonone.retrofit

import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.AuthRequest
import com.practicum.speakeasy.retrofitlessonone.retrofit.auth.User
import com.practicum.speakeasy.retrofitlessonone.retrofit.post.Post
import com.practicum.speakeasy.retrofitlessonone.retrofit.post.Posts
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// Authorizing Resources https://dummyjson.com/docs#intro-auth

interface MainApi {
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User

    @Headers("Content-Type: application/json")
    @GET("auth/posts/{id}")
    suspend fun getPostByIdAuth(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Post

    @Headers("Content-Type: application/json")
    @GET("auth/posts")
    suspend fun getPostsAuth(
        @Header("Authorization") token: String
    ): Posts

    @Headers("Content-Type: application/json")
    @GET("auth/posts/search")
    suspend fun getSearchPostsAuth(
        @Header("Authorization") token: String,
        @Query("q") text: String
    ): Posts
}