package com.practicum.speakeasy.retrofitlessonone.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    @GET("posts")
    suspend fun getPosts(): Posts

    @GET("posts/search")
    suspend fun getSearchPosts(@Query("q") text: String): Posts
}