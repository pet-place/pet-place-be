package com.petplace.be.login.result

data class LoginResult(
    val id: String,
    val nickName: String,
    val accessToken: String,
    val isFirstLogin: Boolean
)
