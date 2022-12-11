package com.petplace.be.user.dto

data class SignUpResult(
    val nickname: String,
    val accessToken: String,
    val refreshToken: String,
    val isSignedUpUser: Boolean
)
