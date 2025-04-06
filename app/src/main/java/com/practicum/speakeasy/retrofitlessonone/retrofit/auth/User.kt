package com.practicum.speakeasy.retrofitlessonone.retrofit.auth

// Login user and get tokens https://dummyjson.com/docs/auth

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String,
)