package com.petplace.be.config.token

data class TokenResult (
    var grantType: String, // Bearer
    var accessToken: String
        )