package com.practicum.speakeasy.retrofitlessonone.retrofit.auth

// Login user and get tokens https://dummyjson.com/docs/auth

data class AuthRequest(
    val username: String,
    val password: String,
)
