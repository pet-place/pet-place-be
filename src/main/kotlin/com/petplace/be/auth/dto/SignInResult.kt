package com.petplace.be.auth.dto

data class SignInResult(
    val nickname: String,
    val accessToken: String?,
    val isSignedUpUser: Boolean
)
