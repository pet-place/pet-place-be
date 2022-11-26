package com.petplace.be.login.param

data class ReLoginParam(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
