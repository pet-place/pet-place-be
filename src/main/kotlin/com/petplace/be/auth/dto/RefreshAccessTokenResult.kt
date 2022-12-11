package com.petplace.be.auth.dto

data class RefreshAccessTokenResult(
    val accessToken: String,
    val refreshToken: String
)
