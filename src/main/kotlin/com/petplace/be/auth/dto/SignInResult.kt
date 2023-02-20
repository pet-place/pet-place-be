package com.petplace.be.auth.dto

data class SignInResult(
    val nickname: String,
    val accessToken: String?,
    val refreshToken: String?,
    val isSignedUpUser: Boolean,
    val profileUrl: String?,
)
