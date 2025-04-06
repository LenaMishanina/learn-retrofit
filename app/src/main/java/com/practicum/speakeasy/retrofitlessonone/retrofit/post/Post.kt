package com.practicum.speakeasy.retrofitlessonone.retrofit.post

// Get a single post https://dummyjson.com/docs/posts

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val tags: List<String>,
    val reactions: Reaction,
    val views: Int,
    val userId: Int,
)

data class Reaction(
    val likes: Int,
    val dislikes: Int,
)
