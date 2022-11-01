package com.petplace.be.config.jwt.dto

data class TokenResult (
    var grantType: String, // Bearer
    var accessToken: String
        )