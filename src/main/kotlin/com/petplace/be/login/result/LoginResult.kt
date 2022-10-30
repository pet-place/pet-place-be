package com.petplace.be.login.result

data class LoginResult(
    val nickName: String,
    val accessToken: String,
    val isFirstLogin: Boolean
)
